package cn.anlper.train.req;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MemberSendCodeReq {
    @NotEmpty(message = "【手机号不能为空】")
    @Pattern(regexp = "^1\\d{10}$", message = "手机号码格式错误")
    private String mobile;
}
