package cn.anlper.train.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 表名：passenger
 * 表注释：乘车人
*/
@Getter
@Setter
@ToString
public class PassengerQueryReq extends PageReq {
    private Long memberId;
}