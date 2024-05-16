package cn.anlper.train.controller.admin;

import cn.anlper.train.req.DailyTrainSeatQueryReq;
import cn.anlper.train.req.DailyTrainSeatSaveReq;
import cn.anlper.train.resp.CommonResp;
import cn.anlper.train.resp.PageResp;
import cn.anlper.train.service.DailyTrainSeatService;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/daily-train-seat")
public class DailyTrainSeatController {

    @Resource
    private DailyTrainSeatService dailyTrainSeatService;

    @PostMapping("/save")
    public CommonResp save(@RequestBody @Validated DailyTrainSeatSaveReq req) {
        dailyTrainSeatService.save(req);
        return CommonResp.ok(null);
    }

    @GetMapping("/query-list")
    public CommonResp queryList(@Validated DailyTrainSeatQueryReq req) {
        PageResp pageResp = dailyTrainSeatService.queryList(req);
        return CommonResp.ok(pageResp);
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable("id") Long id) {
        dailyTrainSeatService.delete(id);
        return CommonResp.ok(null);
    }
}
