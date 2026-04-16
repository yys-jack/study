# Flink 使用指南

## 概述

Apache Flink 是一个分布式流处理引擎，支持有状态的计算。

## 核心概念

- **DataStream** - 数据流，Flink 的基本抽象
- **Source** - 数据源
- **Transformation** - 数据转换
- **Sink** - 数据输出
- **Checkpoint** - 状态检查点

## 常用数据源

### Socket Stream

```java
DataStream<String> source = env.socketTextStream("localhost", 9999);
```

### Kafka Source

```java
FlinkKafkaConsumer<String> consumer = new FlinkKafkaConsumer<>(
    "my-topic",
    new SimpleStringSchema(),
    properties
);
DataStream<String> source = env.addSource(consumer);
```

## 常用 Transformation

| 算子 | 说明 |
|------|------|
| `map` | 一对一转换 |
| `flatMap` | 一对多转换 |
| `filter` | 过滤 |
| `keyBy` | 按键分组 |
| `sum` | 累加 |
| `reduce` | 聚合 |
| `window` | 窗口操作 |

## 状态管理

### 开启 Checkpoint

```java
// 每隔 5 秒做一次 checkpoint
env.enableCheckpointing(5000);

// 配置 checkpoint 选项
CheckpointConfig config = env.getCheckpointConfig();
config.setCheckpointTimeout(60000); // 超时时间
config.setMaxConcurrentCheckpoints(1); // 最大并发数
config.setExternalizedCheckpointCleanup(
    ExternalizedCheckpointCleanup.RETAIN_ON_CANCELLATION);
```

### Exactly-Once 语义

```java
// Kafka 消费者配置
properties.setProperty("isolation.level", "read_committed");
consumer.setStartFromGroupOffsets(); // 从 group offset 开始
```

## 示例：词频统计

```java
Configuration conf = new Configuration();
conf.setString(RestOptions.BIND_PORT, "8081-8089");
StreamExecutionEnvironment env = 
    StreamExecutionEnvironment.createLocalEnvironmentWithWebUI(conf);

env.enableCheckpointing(5000);

DataStream<String> source = env.socketTextStream("localhost", 9999);

source
    .flatMap((String value, Collector<String> out) -> {
        for (String word : value.split(",")) {
            out.collect(word.trim());
        }
    })
    .map(word -> Tuple2.of(word, 1))
    .keyBy(value -> value.f0)
    .sum(1)
    .print();

env.execute("Word Count");
```

## Web UI

本地模式开启 WebUI:

```java
Configuration conf = new Configuration();
conf.setString(RestOptions.BIND_PORT, "8081-8089");
StreamExecutionEnvironment env = 
    StreamExecutionEnvironment.createLocalEnvironmentWithWebUI(conf);
```

访问 http://localhost:8081 查看任务状态。

## FAQ

### Q: Checkpoint 失败怎么办？

1. 检查超时时间是否太短
2. 增加 checkpoint 间隔
3. 检查 state backend 配置

### Q: 如何处理反压？

1. 使用 `env.setBufferTimeout()` 调整缓冲时间
2. 增加并行度
3. 优化算子逻辑
