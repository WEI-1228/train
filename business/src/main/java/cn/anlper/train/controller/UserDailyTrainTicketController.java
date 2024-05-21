package cn.anlper.train.controller;

import cn.anlper.train.req.DailyTrainTicketQueryReq;
import cn.anlper.train.resp.CommonResp;
import cn.anlper.train.resp.PageResp;
import cn.anlper.train.service.DailyTrainTicketService;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/daily-train-ticket")
public class UserDailyTrainTicketController {

    @Resource
    private DailyTrainTicketService dailyTrainTicketService;

    @GetMapping("/query-list")
    public CommonResp queryList(@Validated DailyTrainTicketQueryReq req) {
        PageResp pageResp = dailyTrainTicketService.queryList(req);
        return CommonResp.ok(pageResp);
    }
}
