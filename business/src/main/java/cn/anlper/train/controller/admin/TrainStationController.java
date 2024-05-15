package cn.anlper.train.controller.admin;

import cn.anlper.train.req.trainStationQueryReq;
import cn.anlper.train.req.trainStationSaveReq;
import cn.anlper.train.resp.CommonResp;
import cn.anlper.train.resp.PageResp;
import cn.anlper.train.service.TrainStationService;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/train-station")
public class TrainStationController {

    @Resource
    private TrainStationService trainStationService;

    @PostMapping("/save")
    public CommonResp save(@RequestBody @Validated trainStationSaveReq req) {
        trainStationService.save(req);
        return CommonResp.ok(null);
    }

    @GetMapping("/query-list")
    public CommonResp queryList(@Validated trainStationQueryReq req) {
        PageResp pageResp = trainStationService.queryList(req);
        return CommonResp.ok(pageResp);
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable("id") Long id) {
        trainStationService.delete(id);
        return CommonResp.ok(null);
    }
}
