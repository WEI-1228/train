package cn.anlper.train;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients("cn.anlper.train.feign")
public class BatchApplication8003 {
    public static void main(String[] args) {
        SpringApplication.run(BatchApplication8003.class);
    }
}
