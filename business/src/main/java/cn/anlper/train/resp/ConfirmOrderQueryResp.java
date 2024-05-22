package cn.anlper.train.resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import java.util.Date;

@Data
@ToString
public class ConfirmOrderQueryResp {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    @Column(name = "member_id")
    private Long memberId;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date date;
    private String trainCode;
    private String start;
    private String end;
    private Long dailyTrainTicketId;
    private String status;
    private String tickets;
}
