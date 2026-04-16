# Realtime 实时数据处理模块

本模块包含实时数据流处理相关的示例代码和文档。

## 技术栈

- **Kafka** - 分布式消息队列，用于流式数据摄入
- **Flink** - 流式计算引擎，用于实时数据处理
- **Canal** - MySQL binlog 增量订阅，用于数据同步

## 目录结构

```
realtime/
├── src/
│   ├── main/java/com/wx/study/realtime/
│   │   ├── kafka/    # Kafka 生产者和消费者示例
│   │   ├── flink/    # Flink 流处理示例
│   │   └── canal/    # Canal 客户端示例
│   └── test/java/    # 单元测试
└── docs/
    ├── README.md     # 本文件
    ├── kafka.md      # Kafka 使用指南
    ├── flink.md      # Flink 使用指南
    └── canal.md      # Canal 使用指南
```

## 快速开始

### 1. Kafka 示例

```bash
# 启动 Kafka 消费者
gradle :realtime:run -PmainClass=com.wx.study.realtime.kafka.KafkaConsumerExample

# 启动 Kafka 生产者
gradle :realtime:run -PmainClass=com.wx.study.realtime.kafka.KafkaProducerExample
```

### 2. Flink 示例

```bash
# 先启动 netcat 模拟数据源
nc -lk 9999

# 启动 Flink 词频统计应用
gradle :realtime:run -PmainClass=com.wx.study.realtime.flink.WordCountExample
```

### 3. Canal 示例

```bash
# 确保 Canal Server 已启动
gradle :realtime:run -PmainClass=com.wx.study.realtime.canal.CanalClientExample
```

## 配置说明

各组件的配置参数详见各自的文档：

- [Kafka 使用指南](kafka.md)
- [Flink 使用指南](flink.md)
- [Canal 使用指南](canal.md)

## 数据一致性保证

### Kafka

- `acks=all` - 所有 ISR 副本确认后响应
- `checkpoint` - Flink 定期保存状态和 offset

### Flink

- `enableCheckpointing` - 开启状态检查点
- `exactly-once` - 精确一次语义

## 常见问题

详见各组件文档的 FAQ 部分。
