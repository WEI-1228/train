package cn.anlper.train.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class SkTokenSaveReq {
    private Long id;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date;
    private String trainCode;
    private Integer count;
}
