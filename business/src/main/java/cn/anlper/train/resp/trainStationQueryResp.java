package cn.anlper.train.resp;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

@Data
@ToString
public class trainStationQueryResp {
    @JsonSerialize(using = ToStringSerializer.class)
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
