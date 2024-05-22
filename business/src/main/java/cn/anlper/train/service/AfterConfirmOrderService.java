package cn.anlper.train.service;

import cn.anlper.train.entities.DailyTrainSeat;
import cn.anlper.train.mapper.DailyTrainSeatMapper;
import cn.anlper.train.utils.SnowFlake;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class AfterConfirmOrderService {

    @Resource
    private DailyTrainSeatMapper dailyTrainSeatMapper;

    @Resource
    private SnowFlake snowFlake;

    @Transactional
    public void afterDoConfirm(List<DailyTrainSeat> finalSelectedSeatList) {
        // 修改座位表售卖情况sell
        for (DailyTrainSeat dailyTrainSeat : finalSelectedSeatList) {
            DailyTrainSeat updateSeat = new DailyTrainSeat();
            updateSeat.setId(dailyTrainSeat.getId());
            updateSeat.setSell(dailyTrainSeat.getSell());
            updateSeat.setUpdateTime(new Date());
            dailyTrainSeatMapper.updateByPrimaryKeySelective(updateSeat);
        }


            // 余票详情表修改余票

            // 为会员增加购票记录

            // 更新确认订单为成功
    }

}
