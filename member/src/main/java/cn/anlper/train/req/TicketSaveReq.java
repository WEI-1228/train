package cn.anlper.train.req;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class TicketSaveReq {
    private Long id;
    private Long memberId;
    private Long passengerId;
    private String passengerName;
    private Date trainDate;
    private String trainCode;
    private Integer carriageIndex;
    private String seatRow;
    private String seatCol;
    private String startStation;
    private Date startTime;
    private String endStation;
    private Date endTime;
    private String seatType;
}
