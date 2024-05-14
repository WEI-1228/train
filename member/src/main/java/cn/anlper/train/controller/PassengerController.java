package cn.anlper.train.controller;

import cn.anlper.train.context.LoginMemberContext;
import cn.anlper.train.req.PassengerQueryReq;
import cn.anlper.train.req.PassengerSaveReq;
import cn.anlper.train.resp.CommonResp;
import cn.anlper.train.resp.PassengerQueryResp;
import cn.anlper.train.service.PassengerService;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/query-list")
    public CommonResp queryList(@Validated PassengerQueryReq req) {
        req.setMemberId(LoginMemberContext.getId());
        List<PassengerQueryResp> passengers = passengerService.queryList(req);
        return CommonResp.ok(passengers);
    }
}
