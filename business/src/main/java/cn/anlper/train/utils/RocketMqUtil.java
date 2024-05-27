package cn.anlper.train.utils;

import jakarta.annotation.Resource;
import org.apache.rocketmq.client.apis.ClientConfiguration;
import org.apache.rocketmq.client.apis.ClientException;
import org.apache.rocketmq.client.apis.ClientServiceProvider;
import org.apache.rocketmq.client.apis.producer.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class RocketMqUtil {
    @Autowired
    private ClientServiceProvider provider;

    @Autowired
    private ClientConfiguration configuration;

    @Resource
    private Map<String, Producer> producerMap;


    public Producer getProducer(String topic) {
        if (producerMap.containsKey(topic)) return producerMap.get(topic);
        Producer producer = null;
        try {
            producer = provider.newProducerBuilder()
                    .setTopics(topic)
                    .setClientConfiguration(configuration)
                    .build();
        } catch (ClientException e) {
            throw new RuntimeException("消息队列生产者初始化失败");
        }
        producerMap.put(topic, producer);
        return producer;
    }

}
