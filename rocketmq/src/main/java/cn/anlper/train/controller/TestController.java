package cn.anlper.train.controller;

import cn.anlper.train.utils.RocketMqUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.apis.ClientException;
import org.apache.rocketmq.client.apis.ClientServiceProvider;
import org.apache.rocketmq.client.apis.message.Message;
import org.apache.rocketmq.client.apis.producer.Producer;
import org.apache.rocketmq.client.apis.producer.SendReceipt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class TestController {

    @GetMapping("/hello")
    public String hello() {
        return "hello mq";
    }

    @Resource
    private RocketMqUtil rocketMqUtil;

    @Autowired
    private ClientServiceProvider provider;

    @GetMapping("/send/{msg}")
    public void send(@PathVariable("msg") String msg) throws ClientException {

        // 消息发送的目标Topic名称，需要提前创建。
        String topic = "TestTopic";

        // 初始化Producer时需要设置通信配置以及预绑定的Topic。
        Producer producer = rocketMqUtil.getProducer(topic);
        // 普通消息发送。
        Message message = provider.newMessageBuilder()
                .setTopic(topic)
                // 设置消息索引键，可根据关键字精确查找某条消息。
                .setKeys("messageKey")
                // 设置消息Tag，用于消费端根据指定Tag过滤消息。
                .setTag("messageTag")
                // 消息体。
                .setBody(msg.getBytes())
                .build();
        try {
            // 发送消息，需要关注发送结果，并捕获失败等异常。
            SendReceipt sendReceipt = producer.send(message);
            log.info("Send message successfully, messageId={}", sendReceipt.getMessageId());
        } catch (ClientException e) {
            log.error("Failed to send message", e);
        }
        // producer.close();
    }

}
