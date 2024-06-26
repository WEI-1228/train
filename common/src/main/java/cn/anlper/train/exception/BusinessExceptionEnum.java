package cn.anlper.train.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BusinessExceptionEnum {
    MEMBER_MOBILE_EXIST("手机号已注册"),
    MEMBER_MOBILE_NOT_EXIST("请先获取短信验证码"),
    MEMBER_MOBILE_CODE_ERROR("验证码错误"),

    BUSINESS_STATION_NAME_UNIQUE_ERROR("车站已存在"),

    CONFIRM_ORDER_TICKET_COUNT_ERROR("余票不足"),
    CONFIRM_ORDER_EXCEPTION("服务器忙，请稍后重试"),
    CONFIRM_ORDER_LOCK_FAIL("抢票人数多，请稍后重试"),
    CONFIRM_ORDER_FLOW_EXCEPTION("当前抢票人数多，请稍后重试"),

    CONFIRM_ORDER_TOKEN_FAIL("余票不足，请稍后再来"),
    CONFIRM_ORDER_USER_VISIT_ERROR("操作频率太快，请稍后再试"),

    QUERY_REMAIN_LOCK_FAIL("服务器忙，请稍后重试");
    private final String desc;

}
