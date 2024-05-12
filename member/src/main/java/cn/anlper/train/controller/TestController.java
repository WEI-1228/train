package cn.anlper.train.controller;

import cn.anlper.train.req.MemberRegisterReq;
import cn.anlper.train.req.MemberSendCodeReq;
import cn.anlper.train.resp.CommonResp;
import cn.anlper.train.service.MemberService;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
public class TestController {

    @Resource
    private MemberService memberService;

    @GetMapping("/hello")
    public String hello() {
        return "Hello, World!!!";
    }

    @GetMapping("/count")
    public CommonResp count() {
        int count = memberService.count();
        return CommonResp.ok(count);
    }

    @PostMapping("/register")
    public CommonResp register(@Validated MemberRegisterReq req) {
        Long register = memberService.register(req);
        return CommonResp.ok(register);
    }

    @PostMapping("/send-code")
    public CommonResp sendCode(@Validated MemberSendCodeReq req) {
        memberService.sendCode(req);
        return CommonResp.ok("ok");
    }
}
