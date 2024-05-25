package cn.anlper.train.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RedisTokenEnum {
    // 区分各种业务在Redis中的名称
    LOCK_BUY_TICKET("buy_ticket_lock-"),
    LOCK_QUERY_TOKEN("query_token_lock-"),
    QUERY_TOKEN_NUM("query_token_num-");

    final String prefix;
}
