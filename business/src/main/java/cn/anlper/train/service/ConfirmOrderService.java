package cn.anlper.train.service;

import cn.anlper.train.context.LoginMemberContext;
import cn.anlper.train.entities.ConfirmOrder;
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
            List<Integer> absOffsetList = new ArrayList<>();
            int firstIndex = TrainUtil.getIndex(seatTypeCode, ticketReq0.getSeat());
            for (ConfirmOrderTicketReq ticket: tickets) {
                absOffsetList.add(TrainUtil.getIndex(seatTypeCode, ticket.getSeat()) - firstIndex);
            }
            log.info("本次选票的相对序号为：{}", absOffsetList);
        } else {
            log.info("本次购票没有选座");

        }

        // 选座

            // 一个车厢一个车厢的获取座位数据

            // 挑选符合条件的座位

        // 选中的座位做事务处理：

            // 修改座位表售卖情况sell

            // 余票详情表修改余票

            // 为会员增加购票记录

            // 更新确认订单为成功
    }

    private static void reduceTickets(ConfirmOrderDoReq req, DailyTrainTicket dailyTrainTicket) {
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
