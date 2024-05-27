package cn.anlper.train.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class DailyTrainSeatSaveReq {
    private Long id;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date dailyDate;
    private String trainCode;
    private Integer carriageIndex;
    private String dailyRow;
    private String dailyCol;
    private String seatType;
    private Integer carriageSeatIndex;
    private String sell;
}
