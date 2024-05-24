package cn.anlper.train.service;

import cn.anlper.train.context.LoginMemberContext;
import cn.anlper.train.entities.ConfirmOrder;
import cn.anlper.train.entities.DailyTrainCarriage;
import cn.anlper.train.entities.DailyTrainSeat;
import cn.anlper.train.entities.DailyTrainTicket;
import cn.anlper.train.enums.ConfirmOrderStatusEnum;
import cn.anlper.train.enums.SeatTypeEnum;
import cn.anlper.train.exception.BusinessException;
import cn.anlper.train.exception.BusinessExceptionEnum;
import cn.anlper.train.mapper.ConfirmOrderMapper;
import cn.anlper.train.req.ConfirmOrderDoReq;
import cn.anlper.train.req.ConfirmOrderQueryReq;
import cn.anlper.train.req.ConfirmOrderTicketReq;
import cn.anlper.train.resp.ConfirmOrderQueryResp;
import cn.anlper.train.resp.PageResp;
import cn.anlper.train.utils.SnowFlake;
import cn.anlper.train.utils.TrainUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.EnumUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ConfirmOrderService {
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
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private RedissonClient redissonClient;

    @Resource
    private SnowFlake snowFlake;

    @SentinelResource(value = "doConfirm", blockHandler = "doConfirmBlock")
    public void doConfirm(ConfirmOrderDoReq req) {
        String lockKey = req.getDate() + "-" + req.getTrainCode();
        RLock lock;
        try {
            lock = redissonClient.getLock(lockKey);
            boolean tryLock = lock.tryLock(5, TimeUnit.SECONDS); // 自带看门狗，默认锁30秒，会自动加时
            if (!tryLock) throw new BusinessException(BusinessExceptionEnum.CONFIRM_ORDER_LOCK_FAIL);
        } catch (Exception e) {
            log.error("获取锁出现问题：", e);
            throw new BusinessException(BusinessExceptionEnum.CONFIRM_ORDER_EXCEPTION);
        }

        try {
            // 业务校验，比如车次是否存在，车次是否在有效期等，这里省略
            List<ConfirmOrderTicketReq> tickets = req.getTickets();
            Date date = req.getDate();
            String trainCode = req.getTrainCode();
            String start = req.getStart();
            String end = req.getEnd();

            // 保存确认订单表，订单状态为初始
            Date now = new Date();
            ConfirmOrder confirmOrder = new ConfirmOrder();
            confirmOrder.setId(snowFlake.nextId());
            confirmOrder.setMemberId(LoginMemberContext.getId());
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

            // 查出余票记录，需要得到真实库存
            DailyTrainTicket dailyTrainTicket = dailyTrainTicketService.selectByUnique(date, trainCode, start, end);
            log.info("查出余票记录：{}", JSON.toJSONString(dailyTrainTicket));
            // 扣减余票数量
            reduceTickets(req, dailyTrainTicket);
            Integer startIndex = dailyTrainTicket.getStartIndex();
            Integer endIndex = dailyTrainTicket.getEndIndex();

            // 挑选符合条件的座位

            // 计算所有座位相对第一个座位的相对偏移量，方便后续选座
            ConfirmOrderTicketReq ticketReq0 = tickets.get(0);
            String seat = ticketReq0.getSeat();
            // 必须在外面初始化，不能通过查询的返回，因为我们不能选择已经选过的座位
            // 在连选情况下在外面初始化没问题，单选情况下就很麻烦，直接传入更简单
            List<DailyTrainSeat> finalSelectedSeatList = new ArrayList<>();
            if (StrUtil.isNotBlank(seat)) {
                log.info("本次购票有选座，第一张票的座位是：{}", ticketReq0.getSeat());
                String seatTypeCode = ticketReq0.getSeatTypeCode();
                log.info("本次购票选的是{}等座", seatTypeCode);
                List<Integer> offsetList = new ArrayList<>();
                int firstIndex = TrainUtil.getIndex(seatTypeCode, ticketReq0.getSeat());
                for (ConfirmOrderTicketReq ticket : tickets) {
                    offsetList.add(TrainUtil.getIndex(seatTypeCode, ticket.getSeat()) - firstIndex);
                }
                log.info("本次选票的相对序号为：{}", offsetList);
                // 选座
                getSeat(finalSelectedSeatList, date, trainCode, seatTypeCode, ticketReq0.getSeat().substring(0, 1), offsetList,
                        startIndex, endIndex);

            } else {
                log.info("本次购票没有选座");
                // 选座
                for (ConfirmOrderTicketReq ticket : tickets) {
                    getSeat(finalSelectedSeatList, date, trainCode, ticket.getSeatTypeCode(), null, null,
                            startIndex, endIndex);
                }
            }
            log.info("最终的连选情况：");
            finalSelectedSeatList.forEach(s -> log.info("{}", s.getDailyRow() + s.getDailyCol()));

            // 修改选中座位的sell字段
            for (DailyTrainSeat dailyTrainSeat : finalSelectedSeatList) {
                char[] charArray = dailyTrainSeat.getSell().toCharArray();
                for (int i = startIndex; i < endIndex; i++)
                    charArray[i - 1] = '1';
                String newSell = new String(charArray);
                dailyTrainSeat.setSell(newSell);
            }

            // 选中的座位做事务处理：
            // 修改座位表售卖情况sell
            // 余票详情表修改余票
            // 为会员增加购票记录
            // 更新确认订单为成功
            try {
                afterConfirmOrderService.afterDoConfirm(finalSelectedSeatList, dailyTrainTicket, tickets, confirmOrder);
            } catch (Exception e) {
                log.error("保存购票信息失败", e);
                throw new BusinessException(BusinessExceptionEnum.CONFIRM_ORDER_LOCK_FAIL);
            }
        } finally {
            log.info("购票流程结束，释放锁");
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }

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

    private void reduceTickets(ConfirmOrderDoReq req, DailyTrainTicket dailyTrainTicket) {
        for (ConfirmOrderTicketReq ticket : req.getTickets()) {
            String seatTypeCode = ticket.getSeatTypeCode();
            SeatTypeEnum seatTypeEnum = EnumUtil.getBy(SeatTypeEnum::getCode, seatTypeCode);
            switch (seatTypeEnum) {
                case YDZ -> {
                    int countLeft = dailyTrainTicket.getYdz() - 1;
                    if (countLeft < 0) {
                        throw new BusinessException(BusinessExceptionEnum.CONFIRM_ORDER_TICKET_COUNT_ERROR);
                    }
                    dailyTrainTicket.setYdz(countLeft);
                }
                case EDZ -> {
                    int countLeft = dailyTrainTicket.getEdz() - 1;
                    if (countLeft < 0) {
                        throw new BusinessException(BusinessExceptionEnum.CONFIRM_ORDER_TICKET_COUNT_ERROR);
                    }
                    dailyTrainTicket.setEdz(countLeft);
                }
                case YW -> {
                    int countLeft = dailyTrainTicket.getYw() - 1;
                    if (countLeft < 0) {
                        throw new BusinessException(BusinessExceptionEnum.CONFIRM_ORDER_TICKET_COUNT_ERROR);
                    }
                    dailyTrainTicket.setYw(countLeft);
                }
                case RW -> {
                    int countLeft = dailyTrainTicket.getRw() - 1;
                    if (countLeft < 0) {
                        throw new BusinessException(BusinessExceptionEnum.CONFIRM_ORDER_TICKET_COUNT_ERROR);
                    }
                    dailyTrainTicket.setRw(countLeft);
                }
            }
        }
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
