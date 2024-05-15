package cn.anlper.train.resp;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ${Domain}QueryResp {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

}
