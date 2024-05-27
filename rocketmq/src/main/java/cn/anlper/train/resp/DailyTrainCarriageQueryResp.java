package cn.anlper.train.resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class DailyTrainCarriageQueryResp {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date dailyDate;
    private String trainCode;
    private Integer indexes;
    private String seatType;
    private Integer seatCount;
    private Integer rowCount;
    private Integer colCount;

}
