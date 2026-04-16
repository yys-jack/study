package com.wx.study.realtime.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

/**
 * Kafka 消费者示例
 *
 * 配置说明:
 * - bootstrap.servers: Kafka 集群地址
 * - group.id: 消费者组 ID
 * - key/value.deserializer: 反序列化器
 * - auto.offset.reset: offset 重置策略 (earliest/latest)
 *
 * @author panda
 * @date 2024
 */
public class KafkaConsumerExample {

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "192.168.1.6:9092");
        props.put("group.id", "test-consumer-group");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("auto.offset.reset", "earliest"); // 从头开始消费

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);

        // 订阅主题
        consumer.subscribe(Collections.singletonList("test-topic"));

        System.out.println("开始消费消息...");

        try {
            while (!Thread.currentThread().isInterrupted()) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(5));

                if (records.isEmpty()) {
                    System.out.println("暂无新消息...");
                    continue;
                }

                for (ConsumerRecord<String, String> record : records) {
                    System.out.printf("收到消息：topic=%s, partition=%d, offset=%d, key=%s, value=%s%n",
                            record.topic(),
                            record.partition(),
                            record.offset(),
                            record.key(),
                            record.value());
                }

                // 手动提交 offset（可选，默认自动提交）
                // consumer.commitSync();
            }
        } finally {
            consumer.close();
        }
    }
}
