package cn.anlper.train.controller.admin;

import cn.anlper.train.req.TrainQueryReq;
import cn.anlper.train.req.TrainSaveReq;
import cn.anlper.train.resp.CommonResp;
import cn.anlper.train.resp.PageResp;
import cn.anlper.train.service.TrainService;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/train")
public class TrainController {

    @Resource
    private TrainService trainService;

    @PostMapping("/save")
    public CommonResp save(@RequestBody @Validated TrainSaveReq req) {
        trainService.save(req);
        return CommonResp.ok(null);
    }

    @GetMapping("/query-list")
    public CommonResp queryList(@Validated TrainQueryReq req) {
        PageResp pageResp = trainService.queryList(req);
        return CommonResp.ok(pageResp);
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable("id") Long id) {
        trainService.delete(id);
        return CommonResp.ok(null);
    }
}
