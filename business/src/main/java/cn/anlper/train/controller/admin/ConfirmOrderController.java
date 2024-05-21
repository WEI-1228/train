package cn.anlper.train.controller.admin;

import cn.anlper.train.req.ConfirmOrderQueryReq;
import cn.anlper.train.req.ConfirmOrderSaveReq;
import cn.anlper.train.resp.CommonResp;
import cn.anlper.train.resp.PageResp;
import cn.anlper.train.service.ConfirmOrderService;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/confirm-order")
public class ConfirmOrderController {

    @Resource
    private ConfirmOrderService confirmOrderService;

    @PostMapping("/save")
    public CommonResp save(@RequestBody @Validated ConfirmOrderSaveReq req) {
        confirmOrderService.save(req);
        return CommonResp.ok(null);
    }

    @GetMapping("/query-list")
    public CommonResp queryList(@Validated ConfirmOrderQueryReq req) {
        PageResp pageResp = confirmOrderService.queryList(req);
        return CommonResp.ok(pageResp);
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable("id") Long id) {
        confirmOrderService.delete(id);
        return CommonResp.ok(null);
    }
}
