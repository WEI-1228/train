package cn.anlper.train.controller.admin;

import cn.anlper.train.req.DailyTrainCarriageQueryReq;
import cn.anlper.train.req.DailyTrainCarriageSaveReq;
import cn.anlper.train.resp.CommonResp;
import cn.anlper.train.resp.PageResp;
import cn.anlper.train.service.DailyTrainCarriageService;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/daily-train-carriage")
public class DailyTrainCarriageController {

    @Resource
    private DailyTrainCarriageService dailyTrainCarriageService;

    @PostMapping("/save")
    public CommonResp save(@RequestBody @Validated DailyTrainCarriageSaveReq req) {
        dailyTrainCarriageService.save(req);
        return CommonResp.ok(null);
    }

    @GetMapping("/query-list")
    public CommonResp queryList(@Validated DailyTrainCarriageQueryReq req) {
        PageResp pageResp = dailyTrainCarriageService.queryList(req);
        return CommonResp.ok(pageResp);
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable("id") Long id) {
        dailyTrainCarriageService.delete(id);
        return CommonResp.ok(null);
    }
}
