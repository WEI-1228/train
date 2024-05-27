package cn.anlper.train.service;

import cn.anlper.train.context.LoginMemberContext;
import cn.anlper.train.entities.*;
import cn.anlper.train.enums.ConfirmOrderStatusEnum;
import cn.anlper.train.enums.RedisTokenEnum;
import cn.anlper.train.enums.SeatTypeEnum;
import cn.anlper.train.exception.BusinessException;
import cn.anlper.train.exception.BusinessExceptionEnum;
import cn.anlper.train.mapper.ConfirmOrderMapper;
import cn.anlper.train.req.ConfirmOrderDoReq;
import cn.anlper.train.req.ConfirmOrderQueryReq;
import cn.anlper.train.req.ConfirmOrderTicketReq;
import cn.anlper.train.resp.ConfirmOrderQueryResp;
import cn.anlper.train.resp.PageResp;
import cn.anlper.train.utils.RocketMqUtil;
import cn.anlper.train.utils.SnowFlake;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.EnumUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.apis.ClientException;
import org.apache.rocketmq.client.apis.message.Message;
import org.apache.rocketmq.client.apis.message.MessageBuilder;
import org.apache.rocketmq.client.apis.producer.Producer;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class BeforeConfirmOrderService {
    @Resource
    private ConfirmOrderMapper confirmOrderMapper;
    @Resource
    private DailyTrainTicketService dailyTrainTicketService;
    @Resource
    private DailyTrainCarriageService dailyTrainCarriageService;
    @Resource
    private DailyTrainSeatService dailyTrainSeatService;
    @Resource
    private AfterConfirmOrderService afterConfirmOrderService;
    @Resource
    private SkTokenService skTokenService;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private RedissonClient redissonClient;
    @Resource
    private RocketMqUtil rocketMqUtil;
    @Autowired
    private MessageBuilder messageBuilder;

    @Value("${mq.sell-ticket-topic}")
    private String sellTicketTopic;

    @Resource
    private SnowFlake snowFlake;

    @Value("${spring.profiles.active}")
    private String env;

    @SentinelResource(value = "doConfirm", blockHandler = "doConfirmBlock")
    public void beforeDoConfirm(ConfirmOrderDoReq req) {
        Long id = LoginMemberContext.getId();
        // 开发环境下不需要限制
        if (!env.equals("dev")) {
            // 身份验证，一个用户三秒内只能发送一次抢票请求
            String userToken = RedisTokenEnum.LOCK_BUY_TICKET.getPrefix() + id;
            String exist = redisTemplate.opsForValue().get(userToken);
            if (StrUtil.isNotBlank(exist)) {
                log.info("用户ID【{}】访问频率过快，限制访问", id);
                throw new BusinessException(BusinessExceptionEnum.CONFIRM_ORDER_USER_VISIT_ERROR);
            }
            log.info("设置用户令牌：【{}】", userToken);
            redisTemplate.opsForValue().set(userToken, userToken, 3, TimeUnit.SECONDS);
        }


        // 查询令牌数量
        String trainCode = req.getTrainCode();
        Date date = req.getDate();
        int tokenNum = getTokenNum(trainCode, date);
        // 如果没有令牌了，直接返回没票了
        if (tokenNum <= 0) {
            log.info("没有令牌了，直接抢票失败");
            throw new BusinessException(BusinessExceptionEnum.CONFIRM_ORDER_LOCK_FAIL);
        }
        log.info("获取令牌成功，扣减令牌，开始抢票");
        decreaseTokenNum(trainCode, date);

        // 先保存订单，即使买票失败了也得先记录订单
        List<ConfirmOrderTicketReq> tickets = req.getTickets();

        String start = req.getStart();
        String end = req.getEnd();

        Date now = new Date();
        ConfirmOrder confirmOrder = new ConfirmOrder();
        confirmOrder.setId(snowFlake.nextId());
        confirmOrder.setMemberId(id);
        confirmOrder.setDate(date);
        confirmOrder.setTrainCode(trainCode);
        confirmOrder.setStart(start);
        confirmOrder.setEnd(end);
        confirmOrder.setDailyTrainTicketId(req.getDailyTrainTicketId());
        confirmOrder.setStatus(ConfirmOrderStatusEnum.INIT.getCode());
        confirmOrder.setCreateTime(now);
        confirmOrder.setUpdateTime(now);
        confirmOrder.setTickets(JSON.toJSONString(tickets));
        confirmOrderMapper.insert(confirmOrder);
        log.info("保存用户订单，用户【{}】日期【{}】车次【{}】", id, DateUtil.formatDate(date), trainCode);

        // 传输订单信息
        ConfirmOrderDTO confirmOrderDTO = BeanUtil.copyProperties(confirmOrder, ConfirmOrderDTO.class);
        confirmOrderDTO.setTicketObjList(tickets);
        // 发送抢票信息到消息队列中，开始抢票
        String msg = JSON.toJSONString(confirmOrderDTO);
        JSON.parseObject(msg, ConfirmOrderDTO.class);
        Producer producer = rocketMqUtil.getProducer(sellTicketTopic);
        Message message = messageBuilder
                .setTopic(sellTicketTopic)
                .setKeys(String.valueOf(confirmOrderDTO.getId()))
                .setMessageGroup(trainCode + '-' + date)
                .setBody(msg.getBytes())
                .build();
        try {
            // 发送消息，需要关注发送结果，并捕获失败等异常。
            producer.send(message);
            log.info("订单发送成功, {}", msg);
        } catch (ClientException e) {
            log.error("订单发送出现异常", e);
            throw new RuntimeException();
        }
    }

    private void increaseTokenNum(String trainCode, Date date) {
        String tokenKey = RedisTokenEnum.QUERY_TOKEN_NUM.getPrefix() + date + '-' + trainCode;
        redisTemplate.opsForValue().increment(tokenKey);
    }

    private void decreaseTokenNum(String trainCode, Date date) {
        String tokenKey = RedisTokenEnum.QUERY_TOKEN_NUM.getPrefix() + date + '-' + trainCode;
        redisTemplate.opsForValue().decrement(tokenKey);
    }

    private int getTokenNum(String trainCode, Date date) {
        String tokenKey = RedisTokenEnum.QUERY_TOKEN_NUM.getPrefix() + date + '-' + trainCode;
        // 查看缓存中是否有token的余量
        // 没有的话就进数据库去查
        log.info("开始尝试获取令牌 [{}]", tokenKey);
        if (StrUtil.isBlank(redisTemplate.opsForValue().get(tokenKey))) {
            String tokenLock = RedisTokenEnum.LOCK_QUERY_TOKEN.getPrefix() + date + '-' + trainCode;
            RLock lock = redissonClient.getLock(tokenLock);
            try {
                log.info("没有令牌缓存，尝试回数据库查询令牌数量");
                boolean tryLock = lock.tryLock(5, TimeUnit.SECONDS);
                if (!tryLock) {
                    log.info("争夺令牌查询锁失败，令牌验证失败");
                    return 0;
                }
                if (StrUtil.isBlank(redisTemplate.opsForValue().get(tokenKey))) {
                    int tokenCount = skTokenService.selectCount(date, trainCode);
                    log.info("日期【{}】 车次【{}】的令牌余量为：{}", DateUtil.formatDate(date), trainCode, tokenCount);
                    redisTemplate.opsForValue().set(tokenKey, String.valueOf(tokenCount));
                }
                if (lock.isHeldByCurrentThread()) {
                    log.info("令牌查询结束，释放令牌查询锁");
                    lock.unlock();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return Integer.parseInt(redisTemplate.opsForValue().get(tokenKey));
    }

    /**
     * 如果选座了，就一次选出所有座位，否则一个一个选，因为车厢可能不同
     * TODO 如果没有选座，但都是同一类型的车厢，可以进行优化
     * @param date
     * @param trainCode
     * @param seatType
     * @param column
     * @param offsetList
     */
    private void getSeat(List<DailyTrainSeat> finalSelectedSeatList, Date date, String trainCode,
                                         String seatType, String column, List<Integer> offsetList,
                                         int startIndex, int endIndex) {
        List<DailyTrainCarriage> dailyTrainCarriageList = dailyTrainCarriageService.selectBySeatType(date, trainCode, seatType);
        log.info("共查出{}个符合条件的车厢", dailyTrainCarriageList.size());
        List<DailyTrainSeat> selectedSeatList = new ArrayList<>();
        for (DailyTrainCarriage dailyTrainCarriage: dailyTrainCarriageList) {
            // 一个车厢一个车厢的获取座位数据
            selectedSeatList.clear();
            log.info("开始从车厢{}选座", dailyTrainCarriage.getIndexes());
            List<DailyTrainSeat> dailyTrainSeats = dailyTrainSeatService.selectByCarriage(date, trainCode, dailyTrainCarriage.getIndexes());
            log.info("{}号车厢的座位数：{}", dailyTrainCarriage.getIndexes(), dailyTrainSeats.size());
            for (int i = 0; i < dailyTrainSeats.size(); i++) {
                DailyTrainSeat dailyTrainSeat = dailyTrainSeats.get(i);
                // 首先判断是否选座，如果选座，判断当前座位的列是否匹配
                if (StrUtil.isNotEmpty(column)) {
                    // 不匹配就直接跳过
                    if (!column.equals(dailyTrainSeat.getDailyCol())) {
                        log.info("{}号座位的座位号为{}, 与目标{}不一致，不可选中", i + 1, dailyTrainSeat.getDailyCol(), column);
                        continue;
                    }
                }

                // 预选过了就不能再选，主要是针对不选座的情况判断，连选情况没有这个问题
                boolean inFlag = false;
                for (DailyTrainSeat trainSeat : finalSelectedSeatList) {
                    if (Objects.equals(trainSeat.getId(), dailyTrainSeat.getId())) {
                        inFlag = true;
                        break;
                    }
                }
                if (inFlag) {
                    log.info("{}号座位已经预选，不可再次选择", i + 1);
                    continue;
                }

                boolean isChoose = calSell(dailyTrainSeat, startIndex, endIndex);
                // 第一个座位
                if (isChoose) {
                    log.info("选票成功，座位号是：{}{}", dailyTrainSeat.getDailyRow(), dailyTrainSeat.getDailyCol());
                    selectedSeatList.add(dailyTrainSeat);
                } else {
                    log.info("选票失败");
                    selectedSeatList.clear();
                    continue;
                }
                if (CollUtil.isEmpty(offsetList) || offsetList.size() == 1) {
                    finalSelectedSeatList.addAll(selectedSeatList);
                    return;
                }
                log.info("需要选多张票，偏移值为：{}, 开始尝试连选", offsetList);
                boolean success = true;
                for (int j = 1; j < offsetList.size(); j++) {
                    if (i + j >= dailyTrainSeats.size()) {
                        success = false;
                        log.info("无法继续选座，当前车厢已无可选座位，选座失败");
                        selectedSeatList.clear();
                        break;
                    }
                    int offset = offsetList.get(j);
                    boolean isChooseNext = calSell(dailyTrainSeats.get(i + offset), startIndex, endIndex);
                    if (isChooseNext) {
                        log.info("第{}张票选座成功，座位号为{}{}", j + 1,
                                dailyTrainSeats.get(i + offset).getDailyRow(), dailyTrainSeats.get(i + offset).getDailyCol());
                        selectedSeatList.add(dailyTrainSeats.get(i + offset));
                    } else {
                        success = false;
                        log.info("第{}张票选座失败，整体选座失败", j + 1);
                        selectedSeatList.clear();
                    }
                }
                if (success) {
                    log.info("连选成功");
                    // 保存选好的座位
                    finalSelectedSeatList.addAll(selectedSeatList);
                    return;
                }

            }
        }
    }

    // 1 2 3 4 5 6
    //  0 0 0 0 0
    private boolean calSell(DailyTrainSeat dailyTrainSeat, int startIndex, int endIndex) {
        String sell = dailyTrainSeat.getSell();
        String sellPart = sell.substring(startIndex - 1, endIndex - 1);
        if (Integer.parseInt(sellPart) > 0) {
            log.info("{}号座位（{}{}）在本站区间{}~{}已售过票，不可选中",
                    dailyTrainSeat.getCarriageSeatIndex(),
                    dailyTrainSeat.getDailyRow(),
                    dailyTrainSeat.getDailyCol(),
                    startIndex, endIndex);
            return false;
        } else {
            log.info("{}号座位（{}{}）在本站区间{}~{}未售过票，可选中该座位",
                    dailyTrainSeat.getCarriageSeatIndex(),
                    dailyTrainSeat.getDailyRow(),
                    dailyTrainSeat.getDailyCol(),
                    startIndex, endIndex);
            char[] charArray = sell.toCharArray();
            for (int i = startIndex; i < endIndex; i++)
                charArray[i - 1] = '1';
            String newSell = new String(charArray);
            log.info("完成选座，{}号座位（{}{}）的售卖情况发生变化：{} -> {}",
                    dailyTrainSeat.getCarriageSeatIndex(),
                    dailyTrainSeat.getDailyRow(),
                    dailyTrainSeat.getDailyCol(),
                    sell, newSell);
            return true;
        }
    }

    private boolean reduceTickets(ConfirmOrderDoReq req, DailyTrainTicket dailyTrainTicket) {
        for (ConfirmOrderTicketReq ticket : req.getTickets()) {
            String seatTypeCode = ticket.getSeatTypeCode();
            SeatTypeEnum seatTypeEnum = EnumUtil.getBy(SeatTypeEnum::getCode, seatTypeCode);
            switch (seatTypeEnum) {
                case YDZ -> {
                    int countLeft = dailyTrainTicket.getYdz() - 1;
                    if (countLeft < 0) {
                        return false;
                    }
                    dailyTrainTicket.setYdz(countLeft);
                }
                case EDZ -> {
                    int countLeft = dailyTrainTicket.getEdz() - 1;
                    if (countLeft < 0) {
                        return false;
                    }
                    dailyTrainTicket.setEdz(countLeft);
                }
                case YW -> {
                    int countLeft = dailyTrainTicket.getYw() - 1;
                    if (countLeft < 0) {
                        return false;
                    }
                    dailyTrainTicket.setYw(countLeft);
                }
                case RW -> {
                    int countLeft = dailyTrainTicket.getRw() - 1;
                    if (countLeft < 0) {
                        return false;
                    }
                    dailyTrainTicket.setRw(countLeft);
                }
            }
        }
        return true;
    }

    public PageResp queryList(ConfirmOrderQueryReq req) {
        Example example = new Example(ConfirmOrder.class);
        example.setOrderByClause("update_time desc");
        PageHelper.startPage(req.getPage(), req.getSize());
        List<ConfirmOrder> confirmOrders = confirmOrderMapper.selectByExample(example);
        List<ConfirmOrderQueryResp> confirmOrderQueryResps = BeanUtil.copyToList(confirmOrders, ConfirmOrderQueryResp.class);
        PageInfo<ConfirmOrder> pageInfo = new PageInfo<>(confirmOrders);
        return new PageResp<>(confirmOrderQueryResps, pageInfo.getTotal());
    }

    public void doConfirmBlock(ConfirmOrderDoReq req, BlockException e) {
        log.info("购票请求被限流：{}", req);
        throw new BusinessException(BusinessExceptionEnum.CONFIRM_ORDER_FLOW_EXCEPTION);
    }

}
