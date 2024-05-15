package cn.anlper.train.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TrainSeatQueryReq extends PageReq{
    private Long id;
    private String trainCode;
    private Integer carriageIndex;
    private String seatRow;
    private String seatCol;
    private String seatType;
    private Integer carriageSeatIndex;
}
