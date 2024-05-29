package cn.anlper.train.consumer;

import com.alibaba.fastjson.JSON;
import com.alibaba.otter.canal.client.rocketmq.RocketMQCanalConnector;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.Message;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * RocketMQ client example
 *
 * @author machengyuan @ 2018-6-12
 * @version 1.0.0
 */
@Component
@Slf4j
public class CanalRocketMQClient {

    private RocketMQCanalConnector          connector;
    private static volatile boolean         running = false;
    private Thread                          thread  = null;

    @Value("${mq.nameServers}")
    private String nameServers;
    @Value("${mq.update-ticket-topic}")
    private String topic;
    @Value("${mq.update-ticket-consumer-group}")
    private String groupId;
    @Value("${mq.update-ticket-time-interval}")
    private Long interval;
    @Autowired
    private StringRedisTemplate redisTemplate;


    private final Thread.UncaughtExceptionHandler handler = (t, e) -> log.error("parse events has an error", e);

    @PostConstruct
    public void updateRemainTicketConsumer() {
        try {
            connector = new RocketMQCanalConnector(nameServers, topic, groupId, 500, false);
            log.info("## Start the rocketmq consumer: {}-{}", topic, groupId);
            start();
            log.info("## The canal rocketmq consumer is running now ......");
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    log.info("## Stop the rocketmq consumer");
                    stop();
                } catch (Throwable e) {
                    log.warn("## Something goes wrong when stopping rocketmq consumer:", e);
                } finally {
                    log.info("## Rocketmq consumer is down.");
                }
            }));
        } catch (Throwable e) {
            log.error("## Something going wrong when starting up the rocketmq consumer:", e);
            System.exit(0);
        }
    }

    public void start() {
        Assert.notNull(connector, "connector is null");
        thread = new Thread(this::process);
        thread.setUncaughtExceptionHandler(handler);
        thread.start();
        running = true;
    }

    public void stop() {
        if (!running) {
            return;
        }
        running = false;
        if (thread != null) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                // ignore
            }
        }
    }

    private void process() {
        while (running) {
            try {
                connector.connect();
                connector.subscribe();
                while (running) {
                    // 非阻塞的方法，设置等待时间为1秒，在没有数据的时候，每秒拉取一次，减少资源占用，在有数据的时候会立即获取数据
                    List<Message> messages = connector.getListWithoutAck(interval, TimeUnit.MILLISECONDS); // 获取message
                    for (Message message : messages) {
                        long batchId = message.getId();
                        int size = message.getEntries().size();
                        if (batchId == -1 || size == 0) {
                            // try {
                            // Thread.sleep(1000);
                            // } catch (InterruptedException e) {
                            // }
                        } else {
                            processEntry(message.getEntries());
                            // log.info(message.toString());
                        }
                    }

                    connector.ack(); // 提交确认
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }

        connector.unsubscribe();
        // connector.stopRunning();
    }

    protected void processEntry(List<CanalEntry.Entry> entrys) {
        for (CanalEntry.Entry entry : entrys) {

            if (entry.getEntryType() == CanalEntry.EntryType.TRANSACTIONBEGIN) {
                log.info("事务开始，不处理");
                continue;
            }

            if (entry.getEntryType() == CanalEntry.EntryType.ROWDATA) {
                CanalEntry.RowChange rowChange = null;
                try {
                    rowChange = CanalEntry.RowChange.parseFrom(entry.getStoreValue());
                } catch (Exception e) {
                    throw new RuntimeException("parse event has an error , data:" + entry.toString(), e);
                }

                CanalEntry.EventType eventType = rowChange.getEventType();

                if (eventType == CanalEntry.EventType.QUERY || rowChange.getIsDdl()) {
                    // 查询或DDL（数据库定义语言）就不处理
                    log.info("查询语句，或DDL语句，不处理");
                    continue;
                }

                // 对变化的每一行记录进行处理
                Map<String, Map<String, String>> changeData = new HashMap<>();
                for (CanalEntry.RowData rowData : rowChange.getRowDatasList()) {
                    log.info("---------------------");
                    if (eventType == CanalEntry.EventType.DELETE) {
                        log.info("行记录被删除，不处理");
                    } else {
                        Map<String, String> map = new HashMap<>();
                        String start = null;
                        String end = null;
                        String date = null;
                        String trainCode = null;
                        for (CanalEntry.Column column: rowData.getAfterColumnsList()) {
                            String name = column.getName();
                            String value = column.getValue();
                            switch (name) {
                                case "start" -> start = value;
                                case "end" -> end = value;
                                case "daily_date" -> date = value;
                                case "train_code" -> trainCode = value;
                            }
                            map.put(name, value);
                        }
                        String key = date + '-' + start + '-' + end;
                        String value = JSON.toJSONString(map);
                        changeData.putIfAbsent(key, new HashMap<>());
                        changeData.get(key).put(trainCode, value);
                    }
                }
                // 全部处理完后，更新所有余票信息
                for (var pair: changeData.entrySet()) {
                    String key = pair.getKey();
                    Map<String, String> value = pair.getValue();
                    // 只有缓存中存在该查询项，才进行更新，防止漏了数据
                    if (redisTemplate.hasKey(key)) {
                        for (var p: value.entrySet()) {
                            redisTemplate.opsForHash().put(key, p.getKey(), p.getValue());
                            log.info("更新余票：{}  {}  {}", key, p.getKey(), p.getValue());
                        }
                    }
                }
            }
        }
    }
}