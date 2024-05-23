package cn.anlper.train.req;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
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
