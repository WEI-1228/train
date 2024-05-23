package cn.anlper.train;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("cn.anlper.train.mapper")
@Slf4j
@EnableFeignClients
@EnableCaching
public class BusinessApplication8002 {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(BusinessApplication8002.class);
        ConfigurableEnvironment environment = context.getEnvironment();
        log.info("启动成功，访问地址：\thttp://127.0.0.1:{}", environment.getProperty("server.port"));
    }
}
