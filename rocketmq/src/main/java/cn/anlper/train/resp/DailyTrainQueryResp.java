package cn.anlper.train.resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class DailyTrainQueryResp {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date dailyDate;
    private String code;
    private String type;
    private String start;
    private String startPinyin;
    @JsonFormat(pattern = "HH:mm:ss", timezone = "GMT+8")
    private Date startTime;
    private String end;
    private String endPinyin;
    @JsonFormat(pattern = "HH:mm:ss", timezone = "GMT+8")
    private Date endTime;
}
