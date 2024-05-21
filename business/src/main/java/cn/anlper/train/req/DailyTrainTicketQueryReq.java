package cn.anlper.train.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@ToString
public class DailyTrainTicketQueryReq extends PageReq {
    private Long id;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dailyDate;
    private String trainCode;
    private String start;
    private String startPinyin;
    private Date startTime;
    private Integer startIndex;
    private String end;
    private String endPinyin;
    private Date endTime;
    private Integer endIndex;
}
