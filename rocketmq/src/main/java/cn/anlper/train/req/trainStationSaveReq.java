package cn.anlper.train.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

@Data
@ToString
public class trainStationSaveReq {
    private Long id;
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
