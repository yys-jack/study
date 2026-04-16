# Realtime 实时数据模块 Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** 创建一个新的 `realtime` Gradle 子模块，整合 Kafka、Flink、Canal 实时数据处理相关的代码和文档

**Architecture:** 新增独立子模块 `realtime/`，包含源码目录 `src/main/java/com/wx/study/realtime/` 和文档目录 `docs/`。迁移现有 `streaming` 代码并重构包名，将相关文档从 `docs/interview/` 迁移到模块内。

**Tech Stack:** Java 21, Gradle, Kafka Clients 3.8.0, Flink 1.13.2, Canal Client 1.1.0

---

## File Structure

### 新增文件

| 文件路径 | 说明 |
|---------|------|
| `realtime/build.gradle` | 模块构建配置 |
| `realtime/src/main/java/com/wx/study/realtime/kafka/KafkaConsumerExample.java` | Kafka 消费者示例 |
| `realtime/src/main/java/com/wx/study/realtime/kafka/KafkaProducerExample.java` | Kafka 生产者示例 |
| `realtime/src/main/java/com/wx/study/realtime/flink/WordCountExample.java` | Flink 词频统计示例 |
| `realtime/src/main/java/com/wx/study/realtime/canal/CanalClientExample.java` | Canal 客户端示例 |
| `realtime/docs/README.md` | 模块说明文档 |
| `realtime/docs/kafka.md` | Kafka 使用指南 |
| `realtime/docs/flink.md` | Flink 使用指南 |
| `realtime/docs/canal.md` | Canal 使用指南 |

### 修改文件

| 文件路径 | 修改内容 |
|---------|---------|
| `settings.gradle` | 添加 `include ':realtime'` |
| `build.gradle` | 移除 kafka、flink、canal 依赖（转移到 realtime 模块） |

---

## Task 1: 创建 realtime 模块基础结构

**Files:**
- Create: `realtime/build.gradle`
- Create: `realtime/src/main/java/com/wx/study/realtime/` (directory)
- Create: `realtime/docs/` (directory)

- [ ] **Step 1: 创建 realtime 目录结构**

```bash
mkdir -p realtime/src/main/java/com/wx/study/realtime/kafka
mkdir -p realtime/src/main/java/com/wx/study/realtime/flink
mkdir -p realtime/src/main/java/com/wx/study/realtime/canal
mkdir -p realtime/docs
mkdir -p realtime/src/test/java/com/wx/study/realtime
```

- [ ] **Step 2: 创建 build.gradle 文件**

```groovy
plugins {
    id 'java'
}

group = 'com.wx.study'
version = '1.0.0'

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

dependencies {
    // Kafka
    implementation 'org.apache.kafka:kafka-clients:3.8.0'
    
    // Flink
    implementation 'org.apache.flink:flink-streaming-java_2.11:1.13.2'
    implementation 'org.apache.flink:flink-clients_2.11:1.13.2'
    implementation 'org.apache.flink:flink-runtime-web_2.11:1.13.2'
    
    // Canal
    implementation 'com.alibaba.otter:canal.client:1.1.0'
    
    // Logging
    implementation 'ch.qos.logback:logback-classic:1.5.6'
    
    // Test
    testImplementation 'org.junit.jupiter:junit-jupiter:5.10.2'
    testImplementation 'org.assertj:assertj-core:3.26.0'
}

repositories {
    mavenLocal()
    mavenCentral()
    gradlePluginPortal()
}

test {
    useJUnitPlatform()
    testLogging {
        events "passed", "skipped", "failed"
    }
}
```

- [ ] **Step 3: 验证目录创建成功**

```bash
ls -la realtime/
```
Expected: 显示 `build.gradle`, `src/`, `docs/` 目录

- [ ] **Step 4: 添加 realtime 模块到 settings.gradle**

修改 `settings.gradle`:
```groovy
rootProject.name = 'study'
include ':realtime'
```

- [ ] **Step 5: 验证模块配置**

```bash
./gradlew projects
```
Expected: 输出中包含 `:realtime` 模块

- [ ] **Step 6: 提交**

```bash
git add realtime/ settings.gradle
git commit -m "feat: create realtime module for streaming data processing"
```

---

## Task 2: 迁移 Kafka 代码

**Files:**
- Create: `realtime/src/main/java/com/wx/study/realtime/kafka/KafkaProducerExample.java`
- Create: `realtime/src/main/java/com/wx/study/realtime/kafka/KafkaConsumerExample.java`
- Delete: `src/main/java/com/wx/study/streaming/kafka/KafkaProducerDemo.java`
- Delete: `src/main/java/com/wx/study/streaming/kafka/KafkaConsumerDemo.java`

- [ ] **Step 1: 创建 KafkaProducerExample.java**

```java
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
                System.err.println("消息发送失败: " + exception.getMessage());
                exception.printStackTrace();
            }
        });
        
        // 等待发送完成
        producer.flush();
        producer.close();
    }
}
```

- [ ] **Step 2: 创建 KafkaConsumerExample.java**

```java
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
                    System.out.printf("收到消息: topic=%s, partition=%d, offset=%d, key=%s, value=%s%n",
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
```

- [ ] **Step 3: 编译验证**

```bash
./gradlew :realtime:compileJava
```
Expected: BUILD SUCCESSFUL

- [ ] **Step 4: 删除旧的 streaming/kafka 文件**

```bash
rm src/main/java/com/wx/study/streaming/kafka/KafkaProducerDemo.java
rm src/main/java/com/wx/study/streaming/kafka/KafkaConsumerDemo.java
```

- [ ] **Step 5: 提交**

```bash
git add realtime/src/main/java/com/wx/study/realtime/kafka/ \
        src/main/java/com/wx/study/streaming/kafka/KafkaProducerDemo.java \
        src/main/java/com/wx/study/streaming/kafka/KafkaConsumerDemo.java
git commit -m "refactor: migrate kafka code to realtime module with new package naming"
```

---

## Task 3: 迁移 Flink 代码

**Files:**
- Create: `realtime/src/main/java/com/wx/study/realtime/flink/WordCountExample.java`
- Delete: `src/main/java/com/wx/study/streaming/flink/WordCountApp.java`

- [ ] **Step 1: 创建 WordCountExample.java**

```java
package com.wx.study.realtime.flink;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.configuration.RestOptions;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

/**
 * Flink 词频统计示例
 * 
 * 功能说明:
 * 1. 从 Socket 读取文本数据
 * 2. 按逗号拆分单词
 * 3. 统计每个单词出现的次数
 * 
 * 运行前准备:
 * ```bash
 * nc -lk 9999
 * ```
 * 
 * @author panda
 * @date 2024
 */
public class WordCountExample {
    
    public static void main(String[] args) throws Exception {
        
        // 配置 Flink WebUI
        Configuration conf = new Configuration();
        conf.setString(RestOptions.BIND_PORT, "8081-8089");
        StreamExecutionEnvironment env = 
            StreamExecutionEnvironment.createLocalEnvironmentWithWebUI(conf);
        
        // 定义数据源：从 Socket 读取
        DataStreamSource<String> source = env.socketTextStream("localhost", 9999);
        
        // 数据处理流程
        source
            // 拆分单词
            .flatMap(new FlatMapFunction<String, String>() {
                @Override
                public void flatMap(String value, Collector<String> collector) throws Exception {
                    String[] words = value.split(",");
                    for (String word : words) {
                        collector.collect(word.trim());
                    }
                }
            })
            // 转换为 (word, 1) 元组
            .map(new MapFunction<String, Tuple2<String, Integer>>() {
                @Override
                public Tuple2<String, Integer> map(String value) throws Exception {
                    return new Tuple2<>(value, 1);
                }
            })
            // 按单词分组
            .keyBy(new KeySelector<Tuple2<String, Integer>, String>() {
                @Override
                public String getKey(Tuple2<String, Integer> value) throws Exception {
                    return value.f0;
                }
            })
            // 累加计数
            .sum(1)
            // 打印结果
            .print();
        
        // 执行任务
        env.execute("Word Count Example");
    }
}
```

- [ ] **Step 2: 编译验证**

```bash
./gradlew :realtime:compileJava
```
Expected: BUILD SUCCESSFUL

- [ ] **Step 3: 删除旧的 streaming/flink 文件**

```bash
rm src/main/java/com/wx/study/streaming/flink/WordCountApp.java
```

- [ ] **Step 4: 清理空目录**

```bash
rmdir src/main/java/com/wx/study/streaming/flink 2>/dev/null || true
rmdir src/main/java/com/wx/study/streaming 2>/dev/null || true
```

- [ ] **Step 5: 提交**

```bash
git add realtime/src/main/java/com/wx/study/realtime/flink/ \
        src/main/java/com/wx/study/streaming/flink/WordCountApp.java
git commit -m "refactor: migrate flink code to realtime module with new package naming"
```

---

## Task 4: 迁移 Canal 代码

**Files:**
- Create: `realtime/src/main/java/com/wx/study/realtime/canal/CanalClientExample.java`
- Delete: `src/main/java/com/wx/study/streaming/canal/SimpleCanalClientExample.java`

- [ ] **Step 1: 创建 CanalClientExample.java**

```java
package com.wx.study.realtime.canal;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.Message;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * Canal 客户端示例
 * 
 * 功能说明:
 * 1. 连接 Canal Server
 * 2. 订阅数据库变更
 * 3. 解析 binlog 事件（INSERT/UPDATE/DELETE）
 * 
 * 运行前准备:
 * 1. 启动 Canal Server
 * 2. 配置 instance (example)
 * 3. 确保 MySQL binlog 开启
 * 
 * @author panda
 * @date 2024
 */
public class CanalClientExample {
    
    public static void main(String[] args) {
        // 创建 Canal 连接器
        CanalConnector connector = CanalConnectors.newSingleConnector(
            new InetSocketAddress("192.168.1.6", 11111), // Canal Server 地址
            "example",                                    // instance 名称
            "canal",                                      // 用户名
            "canal"                                       // 密码
        );
        
        int batchSize = 1000;
        int emptyCount = 0;
        
        try {
            // 建立连接
            connector.connect();
            
            // 订阅所有表
            connector.subscribe(".*\\..*");
            
            // 回滚未确认的数据
            connector.rollback();
            
            System.out.println("开始监听 Canal 数据变更...");
            
            while (!Thread.currentThread().isInterrupted()) {
                // 获取数据
                Message message = connector.getWithoutAck(batchSize);
                long batchId = message.getId();
                int size = message.getEntries().size();
                
                if (batchId == -1 || size == 0) {
                    emptyCount++;
                    if (emptyCount % 10 == 0) {
                        System.out.println("暂无新数据，等待中...");
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        break;
                    }
                } else {
                    emptyCount = 0;
                    printEntries(message.getEntries());
                    
                    // 确认消费
                    connector.ack(batchId);
                }
            }
        } catch (Exception e) {
            System.err.println("Canal 客户端异常: " + e.getMessage());
            e.printStackTrace();
        } finally {
            connector.disconnect();
        }
    }
    
    /**
     * 打印 Entry 列表
     */
    private static void printEntries(List<CanalEntry.Entry> entries) {
        for (CanalEntry.Entry entry : entries) {
            // 跳过事务开始/结束标记
            if (entry.getEntryType() == CanalEntry.EntryType.TRANSACTIONBEGIN 
                || entry.getEntryType() == CanalEntry.EntryType.TRANSACTIONEND) {
                continue;
            }
            
            CanalEntry.RowChange rowChange;
            try {
                rowChange = CanalEntry.RowChange.parseFrom(entry.getStoreValue());
            } catch (Exception e) {
                throw new RuntimeException("解析 binlog 事件失败: " + entry, e);
            }
            
            CanalEntry.EventType eventType = rowChange.getEventType();
            System.out.printf("=== binlog[%s:%s], table[%s.%s], event[%s] ===%n",
                    entry.getHeader().getLogfileName(),
                    entry.getHeader().getLogfileOffset(),
                    entry.getHeader().getSchemaName(),
                    entry.getHeader().getTableName(),
                    eventType);
            
            // 打印行数据
            for (CanalEntry.RowData rowData : rowChange.getRowDatasList()) {
                if (eventType == CanalEntry.EventType.DELETE) {
                    printColumns("删除数据", rowData.getBeforeColumnsList());
                } else if (eventType == CanalEntry.EventType.INSERT) {
                    printColumns("插入数据", rowData.getAfterColumnsList());
                } else {
                    printColumns("变更前", rowData.getBeforeColumnsList());
                    printColumns("变更后", rowData.getAfterColumnsList());
                }
            }
        }
    }
    
    /**
     * 打印列信息
     */
    private static void printColumns(String label, List<CanalEntry.Column> columns) {
        System.out.println("  [" + label + "]");
        for (CanalEntry.Column column : columns) {
            System.out.printf("    %s = %s (updated=%s)%n",
                    column.getName(), 
                    column.getValue(), 
                    column.getUpdated());
        }
    }
}
```

- [ ] **Step 2: 编译验证**

```bash
./gradlew :realtime:compileJava
```
Expected: BUILD SUCCESSFUL

- [ ] **Step 3: 删除旧的 streaming/canal 文件**

```bash
rm src/main/java/com/wx/study/streaming/canal/SimpleCanalClientExample.java
```

- [ ] **Step 4: 清理空目录**

```bash
rmdir src/main/java/com/wx/study/streaming/canal 2>/dev/null || true
```

- [ ] **Step 5: 提交**

```bash
git add realtime/src/main/java/com/wx/study/realtime/canal/ \
        src/main/java/com/wx/study/streaming/canal/SimpleCanalClientExample.java
git commit -m "refactor: migrate canal code to realtime module with new package naming"
```

---

## Task 5: 编写模块文档

**Files:**
- Create: `realtime/docs/README.md`
- Create: `realtime/docs/kafka.md`
- Create: `realtime/docs/flink.md`
- Create: `realtime/docs/canal.md`

- [ ] **Step 1: 创建 README.md**

```markdown
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
./gradlew :realtime:run -PmainClass=com.wx.study.realtime.kafka.KafkaConsumerExample

# 启动 Kafka 生产者
./gradlew :realtime:run -PmainClass=com.wx.study.realtime.kafka.KafkaProducerExample
```

### 2. Flink 示例

```bash
# 先启动 netcat 模拟数据源
nc -lk 9999

# 启动 Flink 词频统计应用
./gradlew :realtime:run -PmainClass=com.wx.study.realtime.flink.WordCountExample
```

### 3. Canal 示例

```bash
# 确保 Canal Server 已启动
./gradlew :realtime:run -PmainClass=com.wx.study.realtime.canal.CanalClientExample
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
```

- [ ] **Step 2: 创建 kafka.md**

```markdown
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
```

- [ ] **Step 3: 创建 flink.md**

```markdown
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
```

- [ ] **Step 4: 创建 canal.md**

```markdown
# Canal 使用指南

## 概述

Alibaba Canal 是一个基于 MySQL binlog 的数据同步工具，支持实时订阅 MySQL 数据变更。

## 核心概念

- **binlog** - MySQL 二进制日志，记录数据变更
- **Instance** - Canal 实例，对应一个 MySQL 实例
- **Entry** - 数据条目，包含行变更数据
- **EventType** - 事件类型（INSERT/UPDATE/DELETE）

## 架构

```
MySQL (binlog) → Canal Server → Canal Client → 下游系统
```

## 部署 Canal Server

### 1. 下载

```bash
wget https://github.com/alibaba/canal/releases/download/canal-1.1.6/canal.deployer-1.1.6.tar.gz
tar -xzf canal.deployer-1.1.6.tar.gz
```

### 2. 配置

编辑 `conf/example/instance.properties`:

```properties
# MySQL 地址
canal.instance.master.address=192.168.1.6:3306

# 账号密码
canal.instance.dbUsername=canal
canal.instance.dbPassword=canal

# 过滤表
canal.instance.filter.regex=.*\\..*
```

### 3. 启动

```bash
bin/startup.sh
```

## 客户端配置

### 连接参数

```java
CanalConnector connector = CanalConnectors.newSingleConnector(
    new InetSocketAddress("192.168.1.6", 11111), // Canal Server 地址
    "example",                                    // instance 名称
    "canal",                                      // 用户名
    "canal"                                       // 密码
);
```

### 订阅模式

```java
// 订阅所有表
connector.subscribe(".*\\..*");

// 订阅指定表
connector.subscribe("mydb.mytable");
```

## 数据解析

### 解析 binlog 事件

```java
Message message = connector.getWithoutAck(1000);
List<Entry> entries = message.getEntries();

for (Entry entry : entries) {
    if (entry.getEntryType() == EntryType.ROWDATA) {
        RowChange rowChange = RowChange.parseFrom(entry.getStoreValue());
        EventType eventType = rowChange.getEventType();
        
        switch (eventType) {
            case INSERT:
                handleInsert(rowChange);
                break;
            case UPDATE:
                handleUpdate(rowChange);
                break;
            case DELETE:
                handleDelete(rowChange);
                break;
        }
    }
}
```

### 获取变更数据

```java
// INSERT: 获取 after 数据
for (RowData rowData : rowChange.getRowDatasList()) {
    for (Column column : rowData.getAfterColumnsList()) {
        System.out.println(column.getName() + " = " + column.getValue());
    }
}

// UPDATE: 获取 before 和 after 数据
for (RowData rowData : rowChange.getRowDatasList()) {
    System.out.println("变更前:");
    for (Column column : rowData.getBeforeColumnsList()) {
        System.out.println(column.getName() + " = " + column.getValue());
    }
    System.out.println("变更后:");
    for (Column column : rowData.getAfterColumnsList()) {
        System.out.println(column.getName() + " = " + column.getValue());
    }
}
```

## FAQ

### Q: Canal 连接失败？

1. 检查 Canal Server 是否启动
2. 检查 instance 名称是否正确
3. 检查网络连通性

### Q: 如何过滤特定表？

```java
// 正则表达式过滤
connector.subscribe("mydb\\\\.mytable.*");
```

### Q: 如何处理大量数据？

1. 增加 batchSize
2. 批量确认 (batch ack)
3. 增加消费者并行度
```

- [ ] **Step 5: 提交**

```bash
git add realtime/docs/
git commit -m "docs: add realtime module documentation"
```

---

## Task 6: 迁移 interview/kafka.md 文档

**Files:**
- Modify: `realtime/docs/kafka.md` (合并原有内容)
- Delete: `docs/interview/kafka.md`

- [ ] **Step 1: 读取原有 kafka.md 内容**

```bash
cat docs/interview/kafka.md
```

- [ ] **Step 2: 将原有内容合并到 realtime/docs/kafka.md**

在 `realtime/docs/kafka.md` 末尾追加原有面试文档内容：

```markdown
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
```

- [ ] **Step 3: 删除原有文档**

```bash
rm docs/interview/kafka.md
```

- [ ] **Step 4: 提交**

```bash
git add realtime/docs/kafka.md docs/interview/kafka.md
git commit -m "docs: migrate kafka interview notes to realtime module"
```

---

## Task 7: 根目录 build.gradle 清理

**Files:**
- Modify: `build.gradle`

- [ ] **Step 1: 移除 realtime 相关依赖**

修改根目录 `build.gradle`，移除以下依赖：

```groovy
// 移除这些行：
implementation 'org.apache.kafka:kafka-clients:3.8.0'
implementation 'org.apache.flink:flink-streaming-java_2.11:1.13.2'
implementation 'org.apache.flink:flink-clients_2.11:1.13.2'
implementation 'org.apache.flink:flink-runtime-web_2.11:1.13.2'
implementation 'com.alibaba.otter:canal.client:1.1.0'
```

修改后的 `build.gradle` 应只包含根项目通用依赖。

- [ ] **Step 2: 验证根项目编译**

```bash
./gradlew clean compileJava
```
Expected: BUILD SUCCESSFUL（不包含 realtime 模块的依赖）

- [ ] **Step 3: 提交**

```bash
git add build.gradle
git commit -m "refactor: move realtime dependencies to realtime module"
```

---

## Task 8: 最终验证

**Files:**
- N/A

- [ ] **Step 1: 验证所有模块编译**

```bash
./gradlew clean build
```
Expected: BUILD SUCCESSFUL

- [ ] **Step 2: 运行测试**

```bash
./gradlew test
```
Expected: 所有测试通过

- [ ] **Step 3: 验证模块结构**

```bash
./gradlew projects
```
Expected: 显示 `:realtime` 模块

- [ ] **Step 4: 查看最终目录结构**

```bash
tree -L 3 realtime/
```
Expected: 显示完整的模块结构

---

## 验收标准

- [ ] `realtime/` 模块创建成功，包含 `build.gradle`、`src/`、`docs/`
- [ ] Kafka、Flink、Canal 代码迁移完成，包名更新为 `com.wx.study.realtime.*`
- [ ] 文档完整，包含 README 和各组件使用指南
- [ ] `./gradlew :realtime:compileJava` 编译成功
- [ ] `./gradlew build` 全项目编译成功
- [ ] Git 历史保留（使用 `git mv` 或适当提交）

---

Plan complete and saved to `docs/superpowers/plans/2026-04-15-realtime-module-plan.md`. Two execution options:

**1. Subagent-Driven (recommended)** - I dispatch a fresh subagent per task, review between tasks, fast iteration

**2. Inline Execution** - Execute tasks in this session using executing-plans, batch execution with checkpoints for review

**Which approach?**
