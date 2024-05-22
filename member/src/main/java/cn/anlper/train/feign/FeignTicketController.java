package cn.anlper.train.feign;

import cn.anlper.train.req.MemberTicketSaveReq;
import cn.anlper.train.req.TicketQueryReq;
import cn.anlper.train.req.TicketSaveReq;
import cn.anlper.train.resp.CommonResp;
import cn.anlper.train.resp.PageResp;
import cn.anlper.train.service.TicketService;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/feign/ticket")
public class FeignTicketController {
    @Resource
    private TicketService ticketService;

    @PostMapping("/save")
    public CommonResp save(@RequestBody @Validated MemberTicketSaveReq req) {
        ticketService.save(req);
        return CommonResp.ok(null);
    }

    @GetMapping("/query-list")
    public CommonResp queryList(@Validated TicketQueryReq req) {
        PageResp pageResp = ticketService.queryList(req);
        return CommonResp.ok(pageResp);
    }
}
