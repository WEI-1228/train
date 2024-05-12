package cn.anlper.train.req;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MemberRegisterReq {
    @NotEmpty(message = "【手机号不能为空】")
    private String mobile;
}
