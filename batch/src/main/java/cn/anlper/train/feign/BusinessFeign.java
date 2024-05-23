package cn.anlper.train.feign;

import cn.anlper.train.resp.CommonResp;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Date;

@FeignClient("train-business-service")
//@FeignClient(name = "train-business-service", url = "http://localhost:8002/business")
public interface BusinessFeign {

    @GetMapping("/business/business/hello")
    String hello();

    @GetMapping("/business/admin/daily-train/gen-daily/{date}")
    CommonResp genDaily(@PathVariable("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date);
}
