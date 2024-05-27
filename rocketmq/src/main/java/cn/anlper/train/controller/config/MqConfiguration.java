package cn.anlper.train.controller.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.apis.ClientConfiguration;
import org.apache.rocketmq.client.apis.ClientException;
import org.apache.rocketmq.client.apis.ClientServiceProvider;
import org.apache.rocketmq.client.apis.consumer.ConsumeResult;
import org.apache.rocketmq.client.apis.consumer.FilterExpression;
import org.apache.rocketmq.client.apis.consumer.FilterExpressionType;
import org.apache.rocketmq.client.apis.consumer.PushConsumer;
import org.apache.rocketmq.client.apis.producer.Producer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Configuration
@Slf4j
public class MqConfiguration {
    @Value("${mq.sell-ticket-topic}")
    private String sellTicketTopic;

    @Value("${mq.sell-ticket-consumer-group}")
    private String ConsumerGroup;

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

    @Bean
    public PushConsumer ticketConsumer(ClientServiceProvider provider, ClientConfiguration clientConfiguration) throws ClientException {
        FilterExpression filterExpression = new FilterExpression("*", FilterExpressionType.TAG);
//        需要确保当前消费者分组是顺序投递模式，否则仍然按并发乱序投递
        return provider.newPushConsumerBuilder()
                .setConsumerGroup(ConsumerGroup)
                .setClientConfiguration(clientConfiguration)
                .setSubscriptionExpressions(Collections.singletonMap(sellTicketTopic, filterExpression))
                .setMessageListener(messageView -> {
                    // 消费消息并返回处理结果。
                    ByteBuffer buffer = messageView.getBody();
                    byte[] bytes = new byte[buffer.remaining()];
                    buffer.get(bytes);
                    // 将字节数组转换回字符串
                    String message = new String(bytes);
                    log.info("收到消息：{}", message);
                    return ConsumeResult.SUCCESS;
                })
                .build();
    }

}