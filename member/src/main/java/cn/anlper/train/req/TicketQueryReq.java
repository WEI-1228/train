package cn.anlper.train.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class TicketQueryReq extends PageReq {
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
