package cn.anlper.train.req;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MemberLoginReq {
    @NotEmpty(message = "【手机号不能为空】")
    private String mobile;

    @NotEmpty(message = "【验证码】不能为空")
    private String code;
}
