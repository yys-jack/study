# Realtime Docker Deployment

本目录包含使用 Docker Compose 部署实时数据作业环境的配置文件。

## 架构

```
MySQL (binlog) → Canal → Kafka → Flink → 输出
```

## 组件

| 服务 | 端口 | 说明 |
|------|------|------|
| MySQL | 3306 | 数据源，开启 binlog |
| Kafka | 9092 | 消息队列（KRaft 模式）|
| Canal | 11111 | MySQL binlog 订阅 |
| Flink JM | 8081 | JobManager Web UI |
| Flink TM | - | TaskManager（2 slots）|

## 快速开始

### 1. 启动所有服务

```bash
cd realtime
docker compose up -d
```

### 2. 验证服务状态

```bash
# 查看容器状态
docker compose ps

# 查看 Canal 日志
docker logs realtime-canal -f

# 查看 Kafka 日志
docker logs realtime-kafka -f
```

### 3. 访问 Flink Web UI

浏览器访问：http://localhost:8081

### 4. Kafka 操作

```bash
# 创建测试主题
docker exec realtime-kafka kafka-topics --create --topic test-topic --bootstrap-server localhost:9092

# 查看主题列表
docker exec realtime-kafka kafka-topics --list --bootstrap-server localhost:9092

# 生产消息
docker exec realtime-kafka kafka-console-producer --topic test-topic --bootstrap-server localhost:9092

# 消费消息
docker exec realtime-kafka kafka-console-consumer --topic test-topic --from-beginning --bootstrap-server localhost:9092
```

## MySQL 测试

```bash
# 连接 MySQL
docker exec -it realtime-mysql mysql -uroot -proot123

# 在 MySQL 中测试
USE study_db;
INSERT INTO users (name, email) VALUES ('Test', 'test@example.com');
```

## Canal 测试

Canal 会自动订阅 MySQL binlog 并发送到 Kafka 主题 `canal-topic`。

## Flink 作业提交

### 本地开发模式

在 IDE 中运行 `realtime` 模块的示例代码：

```java
// WordCountExample.java
Configuration conf = new Configuration();
conf.setString(RestOptions.BIND_PORT, "8081-8089");
StreamExecutionEnvironment env = 
    StreamExecutionEnvironment.createLocalEnvironmentWithWebUI(conf);

// 配置 Kafka 消费者
properties.setProperty("bootstrap.servers", "localhost:9092");
```

### 提交到 Docker Flink 集群

```bash
# 打包 JAR
cd realtime
gradle :realtime:jar

# 提交作业
docker cp build/libs/realtime-1.0.0.jar realtime-flink-jobmanager:/opt/flink/usrlib/
docker exec realtime-flink-jobmanager flink run /opt/flink/usrlib/realtime-1.0.0.jar
```

## 停止服务

```bash
# 停止所有服务
docker compose down

# 停止并删除数据卷（谨慎使用）
docker compose down -v
```

## 故障排查

### MySQL 连接失败

```bash
# 检查 MySQL 状态
docker exec realtime-mysql mysqladmin ping -uroot -proot123

# 检查 binlog 状态
docker exec realtime-mysql mysql -uroot -proot123 -e "SHOW MASTER STATUS;"
```

### Canal 连接失败

```bash
# 查看 Canal 日志
docker logs realtime-canal --tail 100

# 检查 Canal 与 MySQL 连通性
docker exec realtime-canal ping -c 3 mysql
```

### Kafka 问题

```bash
# 查看 Kafka 日志
docker logs realtime-kafka --tail 100

# 查看主题状态
docker exec realtime-kafka kafka-topics --describe --topic canal-topic --bootstrap-server localhost:9092
```

## 资源限制

| 服务 | 内存限制 |
|------|----------|
| MySQL | 512MB |
| Kafka | 1GB |
| Canal | 512MB |
| Flink JM | 1GB |
| Flink TM | 1GB |

总内存需求：约 3.5GB
