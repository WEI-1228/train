package cn.anlper.train.controller;

import cn.anlper.train.req.TicketQueryReq;
import cn.anlper.train.req.TicketSaveReq;
import cn.anlper.train.resp.CommonResp;
import cn.anlper.train.resp.PageResp;
import cn.anlper.train.service.TicketService;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/ticket")
public class TicketController {

    @Resource
    private TicketService ticketService;

    @PostMapping("/save")
    public CommonResp save(@RequestBody @Validated TicketSaveReq req) {
        ticketService.save(req);
        return CommonResp.ok(null);
    }

    @GetMapping("/query-list")
    public CommonResp queryList(@Validated TicketQueryReq req) {
        PageResp pageResp = ticketService.queryList(req);
        return CommonResp.ok(pageResp);
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable("id") Long id) {
        ticketService.delete(id);
        return CommonResp.ok(null);
    }
}
