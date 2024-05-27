package cn.anlper.train.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class MemberTicketSaveReq {
    private Long id;
    private Long memberId;
    private Long passengerId;
    private String passengerName;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date trainDate;
    private String trainCode;
    private Integer carriageIndex;
    private String seatRow;
    private String seatCol;
    private String startStation;
    @JsonFormat(pattern = "HH:mm:ss", timezone = "GMT+8")
    private Date startTime;
    private String endStation;
    @JsonFormat(pattern = "HH:mm:ss", timezone = "GMT+8")
    private Date endTime;
    private String seatType;
}
