package cn.anlper.train.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TrainCarriageQueryReq extends PageReq {
    private Long id;
    private String trainCode;
    private Integer indexes;
    private String seatType;
    private Integer seatCount;
    private Integer rowCount;
    private Integer colCount;
}
