package cn.anlper.train.controller.admin;

import cn.anlper.train.req.DailyTrainQueryReq;
import cn.anlper.train.req.DailyTrainSaveReq;
import cn.anlper.train.resp.CommonResp;
import cn.anlper.train.resp.PageResp;
import cn.anlper.train.service.DailyTrainService;
import jakarta.annotation.Resource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/admin/daily-train")
public class DailyTrainController {

    @Resource
    private DailyTrainService dailyTrainService;

    @PostMapping("/save")
    public CommonResp save(@RequestBody @Validated DailyTrainSaveReq req) {
        dailyTrainService.save(req);
        return CommonResp.ok(null);
    }

    @GetMapping("/query-list")
    public CommonResp queryList(@Validated DailyTrainQueryReq req) {
        PageResp pageResp = dailyTrainService.queryList(req);
        return CommonResp.ok(pageResp);
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable("id") Long id) {
        dailyTrainService.delete(id);
        return CommonResp.ok(null);
    }

    @GetMapping("/gen-daily/{date}")
    public CommonResp genDaily(@PathVariable("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        dailyTrainService.genDaily(date);
        return CommonResp.ok(null);
    }
}
