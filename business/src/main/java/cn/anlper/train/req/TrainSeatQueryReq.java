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
    private String row;
    private String col;
    private String seatType;
    private Integer carriageSeatIndex;
}
