package cn.anlper.train.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.apis.ClientConfiguration;
import org.apache.rocketmq.client.apis.ClientServiceProvider;
import org.apache.rocketmq.client.apis.producer.Producer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@Slf4j
public class MqConfiguration {

    @Value("${mq.endpoint}")
    private String endpoint;

    @Bean
    public ClientServiceProvider provider() {
        return ClientServiceProvider.loadService();
    }

    @Bean
    public ClientConfiguration clientConfigurationBuilder() {
        return ClientConfiguration.newBuilder().setEndpoints(endpoint).build();
    }

    @Bean
    public Map<String, Producer> producerMap() {
        return new HashMap<>();
    }

}
