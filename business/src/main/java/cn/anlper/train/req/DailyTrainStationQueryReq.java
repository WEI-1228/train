package cn.anlper.train.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@ToString
public class DailyTrainStationQueryReq extends PageReq {
    private Long id;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date dailyDate;
    private String trainCode;
    private Integer indexes;
    private String name;
    private String namePinyin;
    @JsonFormat(pattern = "HH:mm:ss", timezone = "GMT+8")
    private Date inTime;
    @JsonFormat(pattern = "HH:mm:ss", timezone = "GMT+8")
    private Date outTime;
    @JsonFormat(pattern = "HH:mm:ss", timezone = "GMT+8")
    private Date stopTime;
    private BigDecimal km;

}
