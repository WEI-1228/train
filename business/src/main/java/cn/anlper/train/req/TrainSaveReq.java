package cn.anlper.train.req;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class TrainSaveReq {
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
