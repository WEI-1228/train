package cn.anlper.train.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@ToString
public class trainStationQueryReq extends PageReq{
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
