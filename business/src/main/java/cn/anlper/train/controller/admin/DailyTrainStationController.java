package cn.anlper.train.controller.admin;

import cn.anlper.train.req.DailyTrainStationQueryReq;
import cn.anlper.train.req.DailyTrainStationSaveReq;
import cn.anlper.train.resp.CommonResp;
import cn.anlper.train.resp.PageResp;
import cn.anlper.train.service.DailyTrainStationService;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/daily-train-station")
public class DailyTrainStationController {

    @Resource
    private DailyTrainStationService dailyTrainStationService;

    @PostMapping("/save")
    public CommonResp save(@RequestBody @Validated DailyTrainStationSaveReq req) {
        dailyTrainStationService.save(req);
        return CommonResp.ok(null);
    }

    @GetMapping("/query-list")
    public CommonResp queryList(@Validated DailyTrainStationQueryReq req) {
        PageResp pageResp = dailyTrainStationService.queryList(req);
        return CommonResp.ok(pageResp);
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable("id") Long id) {
        dailyTrainStationService.delete(id);
        return CommonResp.ok(null);
    }
}
