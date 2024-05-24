package cn.anlper.train.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/business")
public class TestController {


    @GetMapping("/hello")
    public String hello() {
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {

        }
        return "Hello, business!!!";
    }

}
