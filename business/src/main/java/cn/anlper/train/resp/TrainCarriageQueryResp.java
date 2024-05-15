package cn.anlper.train.resp;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class TrainCarriageQueryResp {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private String trainCode;
    private Integer index;
    private String seatType;
    private Integer seatCount;
    private Integer rowCount;
    private Integer colCount;
}
