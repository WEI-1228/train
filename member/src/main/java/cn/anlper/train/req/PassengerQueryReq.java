package cn.anlper.train.req;

import lombok.Data;
import lombok.ToString;

/**
 * 表名：passenger
 * 表注释：乘车人
*/
@Data
@ToString
public class PassengerQueryReq {
    private Long memberId;
}