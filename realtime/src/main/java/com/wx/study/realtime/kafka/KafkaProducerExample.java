package com.wx.study.realtime.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Properties;

/**
 * Kafka 生产者示例
 *
 * 配置说明:
 * - bootstrap.servers: Kafka 集群地址
 * - key/value.serializer: 序列化器
 *
 * @author panda
 * @date 2024
 */
public class KafkaProducerExample {

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "192.168.1.6:9092");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        // 可靠性配置（可选）
        // props.put("acks", "all"); // 所有 ISR 副本确认后响应
        // props.put("retries", 3);   // 失败重试次数

        Producer<String, String> producer = new KafkaProducer<>(props);

        // 创建消息记录
        ProducerRecord<String, String> record =
            new ProducerRecord<>("test-topic", "key-1", "Hello from Kafka Producer!");

        // 发送消息并获取回调
        producer.send(record, (metadata, exception) -> {
            if (exception == null) {
                System.out.println("消息发送成功!");
                System.out.println("Topic: " + metadata.topic());
                System.out.println("Partition: " + metadata.partition());
                System.out.println("Offset: " + metadata.offset());
            } else {
                System.err.println("消息发送失败：" + exception.getMessage());
                exception.printStackTrace();
            }
        });

        // 等待发送完成
        producer.flush();
        producer.close();
    }
}
