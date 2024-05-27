package cn.anlper.train.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@ToString
public class SkTokenQueryReq extends PageReq {
    private Long id;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
    private String trainCode;
    private Integer count;

}
