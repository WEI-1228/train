package cn.anlper.train.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BusinessExceptionEnum {
    MEMBER_MOBILE_EXIST("手机号已注册");

    private final String desc;

}
