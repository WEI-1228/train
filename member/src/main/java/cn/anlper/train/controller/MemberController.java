package cn.anlper.train.controller;

import cn.anlper.train.req.MemberLoginReq;
import cn.anlper.train.req.MemberRegisterReq;
import cn.anlper.train.req.MemberSendCodeReq;
import cn.anlper.train.resp.CommonResp;
import cn.anlper.train.resp.MemberLoginResp;
import cn.anlper.train.service.MemberService;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
public class MemberController {

    @Resource
    private MemberService memberService;

    @PostMapping("/register")
    public CommonResp register(@Validated MemberRegisterReq req) {
        Long register = memberService.register(req);
        return CommonResp.ok(register);
    }

    @PostMapping("/send-code")
    public CommonResp sendCode(@RequestBody @Validated MemberSendCodeReq req) {
        memberService.sendCode(req);
        return CommonResp.ok("ok");
    }

    @PostMapping("/login")
    public CommonResp login(@RequestBody @Validated MemberLoginReq req) {
        MemberLoginResp loginResp = memberService.login(req);
        return CommonResp.ok(loginResp);
    }
}
