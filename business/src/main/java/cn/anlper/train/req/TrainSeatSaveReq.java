package cn.anlper.train.req;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class TrainSeatSaveReq {
    private Long id;
    private String trainCode;
    private Integer carriageIndex;
    private String row;
    private String col;
    private String seatType;
    private Integer carriageSeatIndex;
}
