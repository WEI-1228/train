package cn.anlper.train.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class TrainQueryReq extends PageReq {
    private Long id;
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
