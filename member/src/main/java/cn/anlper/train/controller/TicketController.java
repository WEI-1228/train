package cn.anlper.train.controller;

import cn.anlper.train.req.TicketQueryReq;
import cn.anlper.train.resp.CommonResp;
import cn.anlper.train.resp.PageResp;
import cn.anlper.train.service.TicketService;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ticket")
public class TicketController {

    @Resource
    private TicketService ticketService;

    @GetMapping("/query-list")
    public CommonResp queryList(@Validated TicketQueryReq req) {
        PageResp pageResp = ticketService.queryList(req);
        return CommonResp.ok(pageResp);
    }

}
