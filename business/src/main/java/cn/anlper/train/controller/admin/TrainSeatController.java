package cn.anlper.train.controller.admin;

import cn.anlper.train.req.TrainSeatQueryReq;
import cn.anlper.train.req.TrainSeatSaveReq;
import cn.anlper.train.resp.CommonResp;
import cn.anlper.train.resp.PageResp;
import cn.anlper.train.service.TrainSeatService;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/train-seat")
public class TrainSeatController {

    @Resource
    private TrainSeatService trainSeatService;

    @PostMapping("/save")
    public CommonResp save(@RequestBody @Validated TrainSeatSaveReq req) {
        trainSeatService.save(req);
        return CommonResp.ok(null);
    }

    @GetMapping("/query-list")
    public CommonResp queryList(@Validated TrainSeatQueryReq req) {
        PageResp pageResp = trainSeatService.queryList(req);
        return CommonResp.ok(pageResp);
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable("id") Long id) {
        trainSeatService.delete(id);
        return CommonResp.ok(null);
    }
}
