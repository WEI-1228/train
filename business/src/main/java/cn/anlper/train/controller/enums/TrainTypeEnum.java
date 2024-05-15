package cn.anlper.train.controller.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public enum TrainTypeEnum {
    G("G", "高铁", new BigDecimal("1.2")),
    D("D", "动车", new BigDecimal("1")),
    K("K", "快车", new BigDecimal("0.8"));
    private final String code;
    private final String desc;
    private final BigDecimal priceRate;

}
