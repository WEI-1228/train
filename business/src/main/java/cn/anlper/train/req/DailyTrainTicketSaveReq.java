package cn.anlper.train.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

@Data
@ToString
public class DailyTrainTicketSaveReq {
    private Long id;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date dailyDate;
    private String trainCode;
    private String start;
    private String startPinyin;
    @JsonFormat(pattern = "HH:mm:ss", timezone = "GMT+8")
    private Date startTime;
    private Integer startIndex;
    private String end;
    private String endPinyin;
    @JsonFormat(pattern = "HH:mm:ss", timezone = "GMT+8")
    private Date endTime;
    private Integer endIndex;

    private Integer ydz;
    private BigDecimal ydzPrice;
    private Integer edz;
    private BigDecimal edzPrice;
    private Integer rw;
    private BigDecimal rwPrice;
    private Integer yw;
    private BigDecimal ywPrice;
}
