package cn.anlper.train.req;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class TrainSeatSaveReq {
    private Long id;
    private String trainCode;
    private Integer carriageIndex;
    private String seatRow;
    private String seatCol;
    private String seatType;
    private Integer carriageSeatIndex;
}
