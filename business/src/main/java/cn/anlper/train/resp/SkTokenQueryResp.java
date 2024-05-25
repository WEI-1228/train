package cn.anlper.train.resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class SkTokenQueryResp {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date;
    private String trainCode;
    private Integer count;
}
