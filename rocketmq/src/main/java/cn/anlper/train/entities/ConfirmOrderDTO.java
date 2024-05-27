package cn.anlper.train.entities;

import cn.anlper.train.req.ConfirmOrderTicketReq;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


@Data
@ToString
public class ConfirmOrderDTO implements Serializable {
    private Long id;
    private Long memberId;
    @JSONField(format = "yyyy-MM-dd")
    private Date date;
    private String trainCode;
    private String start;
    private String end;
    private Long dailyTrainTicketId;
    private List<ConfirmOrderTicketReq> ticketObjList;
}