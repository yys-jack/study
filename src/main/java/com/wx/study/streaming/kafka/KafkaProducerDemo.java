package com.wxli;

import org.apache.kafka.clients.producer.*;
import java.util.Properties;

public class KafkaProducerDemo {
    public static void main(String[] args) {
        Properties props = new Properties();
        // Kafka 地址
        props.put("bootstrap.servers", "192.168.1.6:9092");
        // 序列化配置（必须）
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer<>(props);
        
        // 创建记录
        ProducerRecord<String, String> record = new ProducerRecord<>("test-topic", "key-1", "Hello from Java!");

        // 发送并获取回调
        producer.send(record, (metadata, exception) -> {
            if (exception == null) {
                System.out.println("消息发送成功！偏移量：" + metadata.offset());
            } else {
                exception.printStackTrace();
            }
        });

        producer.close();
    }
}