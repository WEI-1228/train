package cn.anlper.train.controller.admin;

import cn.anlper.train.req.TrainCarriageQueryReq;
import cn.anlper.train.req.TrainCarriageSaveReq;
import cn.anlper.train.resp.CommonResp;
import cn.anlper.train.resp.PageResp;
import cn.anlper.train.service.TrainCarriageService;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/train-carriage")
public class TrainCarriageController {

    @Resource
    private TrainCarriageService trainCarriageService;

    @PostMapping("/save")
    public CommonResp save(@RequestBody @Validated TrainCarriageSaveReq req) {
        trainCarriageService.save(req);
        return CommonResp.ok(null);
    }

    @GetMapping("/query-list")
    public CommonResp queryList(@Validated TrainCarriageQueryReq req) {
        PageResp pageResp = trainCarriageService.queryList(req);
        return CommonResp.ok(pageResp);
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable("id") Long id) {
        trainCarriageService.delete(id);
        return CommonResp.ok(null);
    }
}
