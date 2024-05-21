package cn.anlper.train.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

//@FeignClient("train-business-service")
@FeignClient(name = "train-business-service", url = "http://localhost:8002/business")
public interface BusinessFeign {

    @GetMapping("/business/hello")
    public String hello();
}
