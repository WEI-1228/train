package cn.anlper.train.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * 表名：passenger
 * 表注释：乘车人
*/
@Data
@ToString
public class PassengerSaveReq {

    private Long id;
    private Long memberId;
    @NotEmpty(message = "【姓名】不能为空")
    private String name;
    @NotEmpty(message = "【身份证】不能为空")
    private String idCard;
    @NotEmpty(message = "【旅客类型】不能为空")
    private String type;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
}