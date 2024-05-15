package cn.anlper.train.resp;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class TrainSeatQueryResp {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private String trainCode;
    private Integer carriageIndex;
    private String row;
    private String col;
    private String seatType;
    private Integer carriageSeatIndex;
}
