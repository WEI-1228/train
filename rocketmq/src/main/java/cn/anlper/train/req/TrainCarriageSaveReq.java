package cn.anlper.train.req;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class TrainCarriageSaveReq {
    private Long id;
    private String trainCode;
    private Integer indexes;
    private String seatType;
    private Integer seatCount;
    private Integer rowCount;
    private Integer colCount;
}
