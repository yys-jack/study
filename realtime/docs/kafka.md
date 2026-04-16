# Kafka 使用指南

## 概述

Apache Kafka 是一个分布式流处理平台，用于构建实时数据流应用。

## 核心概念

- **Topic** - 消息主题，消息按主题分类
- **Partition** - 分区，Topic 的物理分片
- **Producer** - 消息生产者
- **Consumer** - 消息消费者
- **Consumer Group** - 消费者组

## 配置参数

### 生产者关键配置

| 参数 | 说明 | 推荐值 |
|------|------|--------|
| `bootstrap.servers` | Kafka 集群地址 | `host1:9092,host2:9092` |
| `acks` | 确认机制 | `0`/`1`/`all` |
| `retries` | 重试次数 | `3` |
| `batch.size` | 批量发送大小 | `16384` |
| `linger.ms` | 延迟发送时间 | `1` |

### acks 参数说明

- `acks=0` - 不等待确认，最大吞吐量，可能丢失数据
- `acks=1` - Leader 确认即可，平衡吞吐和可靠性
- `acks=all` - 所有 ISR 副本确认，最高可靠性

### 消费者关键配置

| 参数 | 说明 | 推荐值 |
|------|------|--------|
| `bootstrap.servers` | Kafka 集群地址 | `host1:9092,host2:9092` |
| `group.id` | 消费者组 ID | `my-consumer-group` |
| `auto.offset.reset` | offset 重置策略 | `earliest`/`latest` |
| `enable.auto.commit` | 自动提交 offset | `true`/`false` |

### auto.offset.reset 说明

- `earliest` - 从头开始消费（新消费者首次加入时）
- `latest` - 从最新消息开始消费

## 示例代码

### 生产者示例

```java
Properties props = new Properties();
props.put("bootstrap.servers", "localhost:9092");
props.put("acks", "all");
props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

Producer<String, String> producer = new KafkaProducer<>(props);
ProducerRecord<String, String> record = new ProducerRecord<>("my-topic", "key", "value");

producer.send(record, (metadata, exception) -> {
    if (exception == null) {
        System.out.println("发送成功，offset=" + metadata.offset());
    }
});
producer.close();
```

### 消费者示例

```java
Properties props = new Properties();
props.put("bootstrap.servers", "localhost:9092");
props.put("group.id", "my-group");
props.put("auto.offset.reset", "earliest");
props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
consumer.subscribe(Collections.singletonList("my-topic"));

while (!Thread.currentThread().isInterrupted()) {
    ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(5));
    for (ConsumerRecord<String, String> record : records) {
        System.out.printf("收到：%s%n", record.value());
    }
}
consumer.close();
```

## FAQ

### Q: 如何保证消息不丢失？

1. 生产者设置 `acks=all`
2. 消费者手动提交 offset
3. 配合 Flink checkpoint 机制

### Q: 如何保证消息顺序？

1. 单 Partition 保证全局有序
2. 相同 Key 的消息保证分区有序

---

## 面试知识点

### 数据一致性

#### 生产者

主要通过 acks 参数保证：
- `acks=0` - 最大吞吐量，不保证消息一定会被写入
- `acks=1` - 消息写入分区，leader 节点会正确响应，写入失败 leader 节点会响应失败
- `acks=-1/all` - 消息需等待 ISR 所有副本写入成功后响应，可靠性最高

#### Broker

Kafka 服务是分布式、多分区、多副本的

#### 消费者

消费者在接收消息、处理消息、提交 offset 可以异步执行。对于实时数据系统来说，消费端统一使用 Flink 来开发，借助 Flink 的 checkpoint 机制实现数据不丢失。

通过设置 checkpoint 时间，每个一段时间做一次 checkpoint，持久化记录当前的 source 状态和 kafka offset。
