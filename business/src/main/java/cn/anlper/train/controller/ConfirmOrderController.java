package cn.anlper.train.controller;

import cn.anlper.train.req.ConfirmOrderDoReq;
import cn.anlper.train.resp.CommonResp;
import cn.anlper.train.service.ConfirmOrderService;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/confirm-order")
public class ConfirmOrderController {

    @Resource
    private ConfirmOrderService confirmOrderService;

    @PostMapping("/do")
    public CommonResp doConfirm(@RequestBody @Validated ConfirmOrderDoReq req) {
        confirmOrderService.doConfirm(req);
        return CommonResp.ok(null);
    }
}
