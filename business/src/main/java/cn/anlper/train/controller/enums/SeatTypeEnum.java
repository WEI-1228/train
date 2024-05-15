package cn.anlper.train.controller.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
public enum SeatTypeEnum {
    YDZ("1", "一等座", new BigDecimal("0.4")),
    EDZ("1", "二等座", new BigDecimal("0.3")),
    RW("1", "一等座", new BigDecimal("0.6")),
    YW("1", "一等座", new BigDecimal("0.5"));
    private String code;
    private String desc;

    /**
     * N元/公里，公里数*price=票价
     */
    private BigDecimal price;
}
