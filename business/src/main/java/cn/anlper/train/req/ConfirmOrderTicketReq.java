package cn.anlper.train.req;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ConfirmOrderTicketReq {
    @NotBlank(message = "【乘车人ID】不能为空")
    private Long passengerId;

    @NotBlank(message = "【乘车人类型】不能为空")
    private String passengerType;

    @NotBlank(message = "【乘车人姓名】不能为空")
    private String passengerName;

    @NotBlank(message = "【乘车人身份证】不能为空")
    private String passengerIdCard;

    @NotBlank(message = "【作为类型】不能为空")
    private String seatTypeCode;

    /**
     * 选座信息
     */
    private String seat;
}
