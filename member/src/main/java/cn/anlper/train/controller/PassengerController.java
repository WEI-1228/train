package cn.anlper.train.controller;

import cn.anlper.train.req.PassengerSaveReq;
import cn.anlper.train.resp.CommonResp;
import cn.anlper.train.service.PassengerService;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/passenger")
public class PassengerController {

    @Resource
    private PassengerService passengerService;

    @PostMapping("/save")
    public CommonResp save(@RequestBody @Validated PassengerSaveReq req) {
        passengerService.save(req);
        return CommonResp.ok(null);
    }
}
