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
import cn.anlper.train.req.ConfirmOrderTicketReq;
import cn.anlper.train.utils.SnowFlake;
import cn.anlper.train.utils.TrainUtil;
import cn.hutool.core.util.EnumUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private SnowFlake snowFlake;
    public void doConfirm(ConfirmOrderDoReq req) {
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

        // 计算所有座位相对第一个座位的相对偏移量，方便后续选座
        ConfirmOrderTicketReq ticketReq0 = tickets.get(0);
        String seat = ticketReq0.getSeat();
        if (StrUtil.isNotBlank(seat)) {
            log.info("本次购票有选座，第一张票的座位是：{}", ticketReq0.getSeat());
            String seatTypeCode = ticketReq0.getSeatTypeCode();
            log.info("本次购票选的是{}等座", seatTypeCode);
            List<Integer> offsetList = new ArrayList<>();
            int firstIndex = TrainUtil.getIndex(seatTypeCode, ticketReq0.getSeat());
            for (ConfirmOrderTicketReq ticket: tickets) {
                offsetList.add(TrainUtil.getIndex(seatTypeCode, ticket.getSeat()) - firstIndex);
            }
            log.info("本次选票的相对序号为：{}", offsetList);
            // 选座
            getSeat(date, trainCode, seatTypeCode, ticketReq0.getSeat().substring(0, 1), offsetList,
                    dailyTrainTicket.getStartIndex(), dailyTrainTicket.getEndIndex());
        } else {
            log.info("本次购票没有选座");
            // 选座
            for (ConfirmOrderTicketReq ticket: tickets) {
                getSeat(date, trainCode, ticket.getSeatTypeCode(), null, null,
                        dailyTrainTicket.getStartIndex(), dailyTrainTicket.getEndIndex());
            }
        }

            // 挑选符合条件的座位

        // 选中的座位做事务处理：

            // 修改座位表售卖情况sell

            // 余票详情表修改余票

            // 为会员增加购票记录

            // 更新确认订单为成功
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
    private void getSeat(Date date, String trainCode, String seatType, String column,
                         List<Integer> offsetList, int startIndex, int endIndex) {
        List<DailyTrainCarriage> dailyTrainCarriageList = dailyTrainCarriageService.selectBySeatType(date, trainCode, seatType);
        log.info("共查出{}个符合条件的车厢", dailyTrainCarriageList.size());
        for (DailyTrainCarriage dailyTrainCarriage: dailyTrainCarriageList) {
            // 一个车厢一个车厢的获取座位数据
            log.info("开始从车厢{}选座", dailyTrainCarriage.getIndexes());
            List<DailyTrainSeat> dailyTrainSeats = dailyTrainSeatService.selectByCarriage(date, trainCode, dailyTrainCarriage.getIndexes());
            log.info("{}号车厢的座位数：{}", dailyTrainCarriage.getIndexes(), dailyTrainSeats.size());
            for (DailyTrainSeat dailyTrainSeat : dailyTrainSeats) {
                boolean isChoose = calSell(dailyTrainSeat, startIndex, endIndex);
                if (isChoose) {
                    log.info("选票成功，座位号是：{}", dailyTrainSeat.getCarriageSeatIndex());
                    break;
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
            log.info("{}号座位在本站区间{}~{}已售过票，不可选中", dailyTrainSeat.getCarriageSeatIndex(), startIndex, endIndex);
            return false;
        } else {
            log.info("{}号座位在本站区间{}~{}未售过票，可选中该座位", dailyTrainSeat.getCarriageSeatIndex(), startIndex, endIndex);
            char[] charArray = sell.toCharArray();
            for (int i = startIndex; i < endIndex; i++)
                charArray[i - 1] = '1';
            String newSell = new String(charArray);
            log.info("完成选座，{}号座位的售卖情况发生变化：{} -> {}", dailyTrainSeat.getCarriageSeatIndex(), sell, newSell);
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

}
