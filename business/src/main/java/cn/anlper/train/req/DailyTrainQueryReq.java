package cn.anlper.train.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class DailyTrainQueryReq extends PageReq {
    private Long id;
    @JsonFormat(pattern = "yyyy-MM-dd hh:MM:ss", timezone = "GMT+8")
    private Date dailyDate;
    private String code;
    private String type;
    private String start;
    private String startPinyin;
    @JsonFormat(pattern = "hh:MM:ss", timezone = "GMT+8")
    private Date startTime;
    private String end;
    private String endPinyin;
    @JsonFormat(pattern = "hh:MM:ss", timezone = "GMT+8")
    private Date endTime;
}
