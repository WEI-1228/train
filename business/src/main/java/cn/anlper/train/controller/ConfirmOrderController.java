package cn.anlper.train.controller;

import cn.anlper.train.exception.BusinessExceptionEnum;
import cn.anlper.train.req.ConfirmOrderDoReq;
import cn.anlper.train.resp.CommonResp;
import cn.anlper.train.service.ConfirmOrderService;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/confirm-order")
@Slf4j
public class ConfirmOrderController {

    @Resource
    private ConfirmOrderService confirmOrderService;

    @SentinelResource(value = "/confirm-order-ctr", blockHandler = "doConfirmBlock")
    @PostMapping("/do")
    public CommonResp doConfirm(@RequestBody @Validated ConfirmOrderDoReq req) {
        confirmOrderService.doConfirm(req);
        return CommonResp.ok(null);
    }

    public CommonResp doConfirmBlock(@RequestBody @Validated ConfirmOrderDoReq req, BlockException e) {
        log.info("购票请求被限流：{}", req);
        return CommonResp.fail(BusinessExceptionEnum.CONFIRM_ORDER_FLOW_EXCEPTION.getDesc());
    }
}
