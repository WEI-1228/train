package cn.anlper.train.req;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
    @NotNull(message = "【会员ID】不能为空")
    private Long memberId;
    @NotEmpty(message = "【姓名】不能为空")
    private String name;
    @NotEmpty(message = "【身份证】不能为空")
    private String idCard;
    @NotEmpty(message = "【旅客类型】不能为空")
    private String type;

    private Date createTime;

    private Date updateTime;
}