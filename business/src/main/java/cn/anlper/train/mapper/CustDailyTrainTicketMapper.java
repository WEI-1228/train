package cn.anlper.train.mapper;

import cn.anlper.train.entities.DailyTrainTicket;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.Date;

public interface CustDailyTrainTicketMapper extends Mapper<DailyTrainTicket> {
    int updateCountBySell(
            @Param("dailyDate") Date dailyDate,
            @Param("trainCode") String trainCode,
            @Param("seatTypeCode") String seatTypeCode,
            @Param("minStartIndex") int minStartIndex,
            @Param("maxStartIndex") int maxStartIndex,
            @Param("minEndIndex") int minEndIndex,
            @Param("maxEndIndex") int maxEndIndex
    );
}
