package cn.anlper.train.req;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

@Data
@ToString
public class trainStationSaveReq {
    private Long id;
    private String trainCode;
    private Integer index;
    private String name;
    private String namePinyin;
    private Date inTime;
    private Date outTime;
    private Date stopTime;
    private BigDecimal km;
}
