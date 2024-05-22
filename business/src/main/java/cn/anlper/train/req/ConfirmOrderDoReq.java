package cn.anlper.train.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Data
@ToString
public class ConfirmOrderDoReq {

    private Long memberId;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date date;

    private String trainCode;

    private String start;

    private String end;

    private Long dailyTrainTicketId;

    /**
     * 车票
     */
    private List<ConfirmOrderTicketReq> tickets;
}
