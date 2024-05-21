package cn.anlper.train.controller;

import cn.anlper.train.req.TrainQueryReq;
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
@RequestMapping("/train")
public class UserTrainController {

    @Resource
    private TrainService trainService;
    @Resource
    private TrainSeatService trainSeatService;

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

}
