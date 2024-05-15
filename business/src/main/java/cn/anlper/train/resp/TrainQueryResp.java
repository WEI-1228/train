package cn.anlper.train.resp;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class TrainQueryResp {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private String code;
    private String type;
    private String start;
    private String startPinyin;
    private Date startTime;
    private String end;
    private String endPinyin;
    private Date endTime;
}
