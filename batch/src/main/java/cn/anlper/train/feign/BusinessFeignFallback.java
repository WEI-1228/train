package cn.anlper.train.feign;

import cn.anlper.train.resp.CommonResp;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class BusinessFeignFallback implements BusinessFeign{
    @Override
    public String hello() {
        return "出现错误，熔断了！";
    }

    @Override
    public CommonResp genDaily(Date date) {
        return null;
    }
}
