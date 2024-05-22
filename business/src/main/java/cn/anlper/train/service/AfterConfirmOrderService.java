package cn.anlper.train.service;

import cn.anlper.train.context.LoginMemberContext;
import cn.anlper.train.controller.feign.MemberFeign;
import cn.anlper.train.entities.DailyTrainSeat;
import cn.anlper.train.entities.DailyTrainTicket;
import cn.anlper.train.mapper.CustDailyTrainTicketMapper;
import cn.anlper.train.mapper.DailyTrainSeatMapper;
import cn.anlper.train.req.ConfirmOrderTicketReq;
import cn.anlper.train.req.MemberTicketSaveReq;
import cn.anlper.train.resp.CommonResp;
import cn.anlper.train.utils.SnowFlake;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class AfterConfirmOrderService {

    @Resource
    private DailyTrainSeatMapper dailyTrainSeatMapper;
    @Resource
    private CustDailyTrainTicketMapper custDailyTrainTicketMapper;
    @Resource
    private MemberFeign memberFeign;

    @Resource
    private SnowFlake snowFlake;

    @Transactional
    public void afterDoConfirm(List<DailyTrainSeat> finalSelectedSeatList, DailyTrainTicket dailyTrainTicket,
                               List<ConfirmOrderTicketReq> tickets) {
        for (int i = 0; i < finalSelectedSeatList.size(); i++) {
            DailyTrainSeat dailyTrainSeat = finalSelectedSeatList.get(i);
            // 修改座位表售卖情况sell
            DailyTrainSeat updateSeat = new DailyTrainSeat();
            updateSeat.setId(dailyTrainSeat.getId());
            updateSeat.setSell(dailyTrainSeat.getSell());
            updateSeat.setUpdateTime(new Date());
            dailyTrainSeatMapper.updateByPrimaryKeySelective(updateSeat);

            // 余票详情表修改余票
            char[] charArray = dailyTrainSeat.getSell().toCharArray();
            int startIndex = dailyTrainTicket.getStartIndex();
            int endIndex = dailyTrainTicket.getEndIndex();
            int minStart = startIndex;
            while (minStart > 1 && charArray[minStart - 2] == '0') {
                minStart--;
            }
            int maxEnd = endIndex;
            while (maxEnd <= charArray.length && charArray[maxEnd - 1] == '0') {
                maxEnd++;
            }
            int maxStart = endIndex - 1;
            int minEnd = startIndex + 1;
            log.info("影响出发站的区间：[{}, {}]", minStart, maxStart);
            log.info("影响终点站的区间：[{}, {}]", minEnd, maxEnd);
            custDailyTrainTicketMapper.updateCountBySell(
                    dailyTrainTicket.getDailyDate(),
                    dailyTrainTicket.getTrainCode(),
                    dailyTrainSeat.getSeatType(),
                    minStart, maxStart, minEnd, maxEnd
            );
            // 为会员增加购票记录
            MemberTicketSaveReq memberTicketSaveReq = new MemberTicketSaveReq();
            memberTicketSaveReq.setMemberId(LoginMemberContext.getId());
            memberTicketSaveReq.setPassengerId(tickets.get(i).getPassengerId());
            memberTicketSaveReq.setPassengerName(tickets.get(i).getPassengerName());
            memberTicketSaveReq.setTrainDate(dailyTrainTicket.getDailyDate());
            memberTicketSaveReq.setTrainCode(dailyTrainTicket.getTrainCode());
            memberTicketSaveReq.setCarriageIndex(dailyTrainSeat.getCarriageIndex());
            memberTicketSaveReq.setSeatRow(dailyTrainSeat.getDailyRow());
            memberTicketSaveReq.setSeatCol(dailyTrainSeat.getDailyCol());
            memberTicketSaveReq.setStartStation(dailyTrainTicket.getStart());
            memberTicketSaveReq.setStartTime(dailyTrainTicket.getStartTime());
            memberTicketSaveReq.setEndStation(dailyTrainTicket.getEnd());
            memberTicketSaveReq.setEndTime(dailyTrainTicket.getEndTime());
            memberTicketSaveReq.setSeatType(dailyTrainSeat.getSeatType());

            CommonResp commonResp = memberFeign.save(memberTicketSaveReq);
            log.info("调用Member接口保存订单信息，返回：{}", commonResp);
            // 更新确认订单为成功
        }





    }

}
