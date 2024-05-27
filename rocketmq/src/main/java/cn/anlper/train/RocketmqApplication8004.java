package cn.anlper.train;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("cn.anlper.train.mapper")
@EnableFeignClients
public class RocketmqApplication8004 {
    public static void main(String[] args) {
        SpringApplication.run(RocketmqApplication8004.class);
    }
}
