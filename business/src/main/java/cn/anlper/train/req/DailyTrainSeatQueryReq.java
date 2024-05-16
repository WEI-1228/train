package cn.anlper.train.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class DailyTrainSeatQueryReq extends PageReq {
    private Long id;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date dailyDate;
    private String trainCode;
    private Integer carriageIndex;
    private String seatRow;
    private String seatCol;
    private String seatType;
    private Integer carriageSeatIndex;
}
