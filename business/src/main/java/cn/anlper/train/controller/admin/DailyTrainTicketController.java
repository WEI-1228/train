package cn.anlper.train.controller.admin;

import cn.anlper.train.req.DailyTrainTicketQueryReq;
import cn.anlper.train.req.DailyTrainTicketSaveReq;
import cn.anlper.train.resp.CommonResp;
import cn.anlper.train.resp.PageResp;
import cn.anlper.train.service.DailyTrainTicketService;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/daily-train-ticket")
public class DailyTrainTicketController {

    @Resource
    private DailyTrainTicketService dailyTrainTicketService;

    @PostMapping("/save")
    public CommonResp save(@RequestBody @Validated DailyTrainTicketSaveReq req) {
        dailyTrainTicketService.save(req);
        return CommonResp.ok(null);
    }

    @GetMapping("/query-list")
    public CommonResp queryList(@Validated DailyTrainTicketQueryReq req) {
        PageResp pageResp = dailyTrainTicketService.queryList(req);
        return CommonResp.ok(pageResp);
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable("id") Long id) {
        dailyTrainTicketService.delete(id);
        return CommonResp.ok(null);
    }
}
