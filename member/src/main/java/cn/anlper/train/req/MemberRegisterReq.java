package cn.anlper.train.req;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MemberRegisterReq {
    @NotBlank(message = "【手机号不能为空】")
    private String mobile;
}
