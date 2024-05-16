package cn.anlper.train.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@ToString
public class DailyTrainCarriageQueryReq extends PageReq {
    private Long id;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dailyDate;
    private String trainCode;
    private Integer indexes;
    private String seatType;
    private Integer seatCount;
    private Integer rowCount;
    private Integer colCount;

}
