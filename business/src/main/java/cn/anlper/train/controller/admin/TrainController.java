package cn.anlper.train.controller.admin;

import cn.anlper.train.req.TrainQueryReq;
import cn.anlper.train.req.TrainSaveReq;
import cn.anlper.train.resp.CommonResp;
import cn.anlper.train.resp.PageResp;
import cn.anlper.train.resp.TrainQueryResp;
import cn.anlper.train.service.TrainSeatService;
import cn.anlper.train.service.TrainService;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/train")
public class TrainController {

    @Resource
    private TrainService trainService;
    @Resource
    private TrainSeatService trainSeatService;

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

    @GetMapping("/query-all")
    public CommonResp queryAll() {
        List<TrainQueryResp> trainQueryRespList = trainService.queryAll();
        return CommonResp.ok(trainQueryRespList);
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable("id") Long id) {
        trainService.delete(id);
        return CommonResp.ok(null);
    }

    @GetMapping("/gen-seat/{trainCode}")
    public CommonResp genSeat(@PathVariable("trainCode") String trainCode) {
        trainSeatService.genTrainSeat(trainCode);
        return CommonResp.ok(null);
    }
}
