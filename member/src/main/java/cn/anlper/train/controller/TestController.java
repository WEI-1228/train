package cn.anlper.train.controller;

import cn.anlper.train.service.MemberService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
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
    public int mob() {
        return memberService.count();
    }
}
