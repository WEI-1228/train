package cn.anlper.train.controller.admin;

import cn.anlper.train.req.ConfirmOrderQueryReq;
import cn.anlper.train.resp.CommonResp;
import cn.anlper.train.service.ConfirmOrderService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/confirm-order")
public class AdminConfirmOrderController {

    @Resource
    private ConfirmOrderService confirmOrderService;

    @GetMapping("/query-list")
    public CommonResp queryList(ConfirmOrderQueryReq req) {
        return CommonResp.ok(confirmOrderService.queryList(req));
    }
}
