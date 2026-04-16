# Realtime Docker Deploy Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** 使用 Docker Compose 部署一套完整的实时数据作业环境（MySQL + Kafka KRaft + Canal + Flink）

**Architecture:** 采用 Docker Compose 编排所有服务，MySQL 开启 binlog 供 Canal 订阅，Canal 将数据变更发送到 Kafka，Flink Standalone 集群消费 Kafka 数据进行实时计算，支持本地 IDE 提交作业。

**Tech Stack:** Docker Compose, MySQL 8.0, Kafka 7.5.0 (KRaft), Canal 1.1.6, Flink 1.16

---

## File Structure

**新建文件：**
- `realtime/docker-compose.yml` - Docker Compose 编排文件
- `realtime/docker/mysql/init.sql` - MySQL 初始化脚本（用户 + 权限）
- `realtime/docker/mysql/my.cnf` - MySQL 配置文件（binlog 配置）
- `realtime/docker/canal/canal.properties` - Canal 服务器配置
- `realtime/docker/canal/instance.properties` - Canal 实例配置
- `realtime/docker/flink/flink-conf.yaml` - Flink 配置
- `realtime/docker/README.md` - 部署和使用说明文档

---

### Task 1: 创建 Docker Compose 编排文件

**Files:**
- Create: `realtime/docker-compose.yml`

- [ ] **Step 1: 创建 docker-compose.yml 文件**

```yaml
version: '3.8'

services:
  # ==================== MySQL ====================
  mysql:
    image: mysql:8.0
    container_name: realtime-mysql
    restart: always
    environment:
      MYSQL_ROOT_PASSWORD: root123
      MYSQL_DATABASE: study_db
      MYSQL_USER: canal
      MYSQL_PASSWORD: canal
    ports:
      - "3306:3306"
    volumes:
      - ./docker/mysql/my.cnf:/etc/mysql/conf.d/my.cnf:ro
      - ./docker/mysql/init.sql:/docker-entrypoint-initdb.d/init.sql:ro
      - mysql_data:/var/lib/mysql
    mem_limit: 512m
    networks:
      - realtime-net

  # ==================== Kafka (KRaft) ====================
  kafka:
    image: confluentinc/cp-kafka:7.5.0
    container_name: realtime-kafka
    restart: always
    environment:
      # KRaft 模式不需要 Zookeeper
      CLUSTER_ID: "realtime-cluster-001"
      # 启用 KRaft 模式
      KAFKA_KRAFT_MODE: "true"
      # Controller 配置
      KAFKA_CONTROLLER_QUORUM_VOTERS: "1@localhost:9093"
      KAFKA_CONTROLLER_LISTENER_NAMES: "CONTROLLER"
      # Listener 配置
      KAFKA_LISTENERS: "PLAINTEXT://0.0.0.0:9092,CONTROLLER://0.0.0.0:9093"
      KAFKA_ADVERTISED_LISTENERS: "PLAINTEXT://localhost:9092"
      KAFKA_LISTENER_SECURITY_PROTOCOL_MAP: "CONTROLLER:PLAINTEXT,PLAINTEXT:PLAINTEXT"
      # 节点角色
      KAFKA_NODE_ID: "1"
      KAFKA_PROCESS_ROLES: "controller,broker"
      # 日志目录
      KAFKA_LOG_DIRS: "/tmp/kraft-combined-logs"
      # 主题配置
      OFFSETS_TOPIC_REPLICATION_FACTOR: "1"
      TRANSACTION_STATE_LOG_REPLICATION_FACTOR: "1"
      TRANSACTION_STATE_LOG_MIN_ISR: "1"
    ports:
      - "9092:9092"
    mem_limit: 1g
    networks:
      - realtime-net

  # ==================== Canal ====================
  canal:
    image: canal/canal-server:v1.1.6
    container_name: realtime-canal
    restart: always
    environment:
      - canal.instance.master.address=mysql:3306
      - canal.instance.dbUsername=canal
      - canal.instance.dbPassword=canal
      - canal.instance.filter.regex=.*\\..*
    ports:
      - "11111:11111"
    volumes:
      - ./docker/canal/canal.properties:/home/admin/canal-server/conf/canal.properties:ro
      - ./docker/canal/instance.properties:/home/admin/canal-server/conf/example/instance.properties:ro
    mem_limit: 512m
    networks:
      - realtime-net
    depends_on:
      - mysql
      - kafka

  # ==================== Flink JobManager ====================
  flink-jobmanager:
    image: flink:1.16
    container_name: realtime-flink-jobmanager
    restart: always
    command: jobmanager
    environment:
      - FLINK_PROPERTIES=
        jobmanager.rpc.address: realtime-flink-jobmanager
    ports:
      - "8081:8081"
    mem_limit: 1g
    networks:
      - realtime-net

  # ==================== Flink TaskManager ====================
  flink-taskmanager:
    image: flink:1.16
    container_name: realtime-flink-taskmanager
    restart: always
    command: taskmanager
    environment:
      - FLINK_PROPERTIES=
        jobmanager.rpc.address: realtime-flink-jobmanager
        taskmanager.numberOfTaskSlots: 2
    mem_limit: 1g
    networks:
      - realtime-net
    depends_on:
      - flink-jobmanager

volumes:
  mysql_data:

networks:
  realtime-net:
    driver: bridge
```

- [ ] **Step 2: 验证 YAML 语法**

```bash
cd /home/yy/workspace/IdeaProjects/study/realtime
python3 -c "import yaml; yaml.safe_load(open('docker-compose.yml'))"
```
Expected: 无输出（无错误表示 YAML 合法）

- [ ] **Step 3: 提交**

```bash
git add realtime/docker-compose.yml
git commit -m "feat: add docker-compose.yml for realtime data pipeline"
```

---

### Task 2: 创建 MySQL 配置文件

**Files:**
- Create: `realtime/docker/mysql/my.cnf`

- [ ] **Step 1: 创建 MySQL 配置文件**

```ini
[mysqld]
# 开启 binlog
log-bin=mysql-bin
binlog-format=ROW
server-id=1

# Canal 需要的配置
gtid_mode=on
enforce_gtid_consistency=on
binlog_row_image=FULL

# 性能优化（内存限制下）
innodb_buffer_pool_size=128M
max_connections=50
```

- [ ] **Step 2: 提交**

```bash
git add realtime/docker/mysql/my.cnf
git commit -m "feat: add mysql config with binlog enabled"
```

---

### Task 3: 创建 MySQL 初始化脚本

**Files:**
- Create: `realtime/docker/mysql/init.sql`

- [ ] **Step 1: 创建初始化 SQL 脚本**

```sql
-- 创建 Canal 用户并授权（如果环境变量未自动创建）
CREATE USER IF NOT EXISTS 'canal'@'%' IDENTIFIED BY 'canal';
GRANT SELECT, REPLICATION SLAVE, REPLICATION CLIENT, SHOW VIEW ON *.* TO 'canal'@'%';
FLUSH PRIVILEGES;

-- 创建示例表用于测试
USE study_db;

CREATE TABLE IF NOT EXISTS users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    email VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 插入测试数据
INSERT INTO users (name, email) VALUES 
    ('Alice', 'alice@example.com'),
    ('Bob', 'bob@example.com'),
    ('Charlie', 'charlie@example.com');

-- 创建示例数据表
CREATE TABLE IF NOT EXISTS orders (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    product VARCHAR(100),
    amount DECIMAL(10, 2),
    status TINYINT DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 创建 Kafka 主题相关表（Flink 输出用）
CREATE TABLE IF NOT EXISTS flink_output (
    id INT PRIMARY KEY AUTO_INCREMENT,
    word VARCHAR(50) NOT NULL,
    count BIGINT DEFAULT 0,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
```

- [ ] **Step 2: 提交**

```bash
git add realtime/docker/mysql/init.sql
git commit -m "feat: add mysql init script with canal user and test tables"
```

---

### Task 4: 创建 Canal 配置文件

**Files:**
- Create: `realtime/docker/canal/canal.properties`
- Create: `realtime/docker/canal/instance.properties`

- [ ] **Step 1: 创建 Canal 服务器配置**

```properties
# Canal 服务器配置
canal.id = 1
canal.ip =
canal.port = 11111

# Kafka 配置
canal.serverMode = kafka
canal.kafka.bootstrap.servers = kafka:9092

# Kafka 生产者配置
canal.mq.send.batch.size = 16384
canal.mq.send.buffer.size = 32768
canal.mq.retries = 3
canal.mq.acks = all

# 主题配置
canal.mq.topic=canal-topic
canal.mq.dynamicTopic=.*\\..*

# 序列化配置
canal.mq.canalEntryJsonFormat = true

# 过滤配置
canal.instance.filter.regex = .*\\..*

# 内存配置
canal.instance.memory.size = 256
canal.instance.memory.buffer.size = 128
```

- [ ] **Step 2: 创建 Canal 实例配置**

```properties
# 数据库地址（Docker 网络中）
canal.instance.master.address=mysql:3306

# 数据库账号
canal.instance.dbUsername = canal
canal.instance.dbPassword = canal

# 使用 GTID 模式
canal.instance.mysql.slaveMode = false
canal.instance.gtidon = true

# 过滤配置
canal.instance.filter.regex = .*\\..*

# 字符集
canal.instance.defaultDatabaseName = study_db

# Kafka 配置
canal.mq.topic = canal-topic
canal.mq.dynamicTopic = .*\\..*
```

- [ ] **Step 3: 提交**

```bash
git add realtime/docker/canal/canal.properties realtime/docker/canal/instance.properties
git commit -m "feat: add canal server and instance configuration"
```

---

### Task 5: 创建 Flink 配置文件

**Files:**
- Create: `realtime/docker/flink/flink-conf.yaml`

- [ ] **Step 1: 创建 Flink 配置**

```yaml
# JobManager 配置
jobmanager.rpc.address: realtime-flink-jobmanager
jobmanager.rpc.port: 6123
jobmanager.bind-host-address: 0.0.0.0
jobmanager.web.address: 0.0.0.0
jobmanager.web.port: 8081

# TaskManager 配置
taskmanager.numberOfTaskSlots: 2
taskmanager.bind-host-address: 0.0.0.0
taskmanager.data.port: 6121

# 网络配置
taskmanager.network.memory.fraction: 0.2
taskmanager.network.memory.min: 64mb
taskmanager.network.memory.max: 1gb

# Checkpoint 配置
state.backend: filesystem
state.checkpoints.dir: file:///tmp/flink-checkpoints
state.savepoints.dir: file:///tmp/flink-savepoints

# 容错配置
execution.checkpointing.interval: 10000
execution.checkpointing.timeout: 60000
execution.checkpointing.max-concurrent-checkpoints: 1
execution.checkpointing.externalized-checkpoint-retention: RETAIN_ON_CANCELLATION

# 重启策略
restart-strategy: fixed-delay
restart-strategy.fixed-delay.attempts: 3
restart-strategy.fixed-delay.delay: 10s

# 日志配置
env.java.opts: "-Xmx512m"
```

- [ ] **Step 2: 提交**

```bash
git add realtime/docker/flink/flink-conf.yaml
git commit -m "feat: add flink configuration with checkpoint settings"
```

---

### Task 6: 创建部署说明文档

**Files:**
- Create: `realtime/docker/README.md`

- [ ] **Step 1: 创建 README 文档**

```markdown
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
docker-compose up -d
```

### 2. 验证服务状态

```bash
# 查看容器状态
docker-compose ps

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
docker-compose down

# 停止并删除数据卷（谨慎使用）
docker-compose down -v
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
```

- [ ] **Step 2: 提交**

```bash
git add realtime/docker/README.md
git commit -m "docs: add docker deployment guide"
```

---

### Task 7: 创建 docker 目录结构脚本

**Files:**
- Create: `realtime/docker/setup.sh`

- [ ] **Step 1: 创建目录结构脚本**

```bash
#!/bin/bash
# 创建 docker 配置目录

set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

# 创建目录
mkdir -p "$SCRIPT_DIR/mysql"
mkdir -p "$SCRIPT_DIR/canal/example"
mkdir -p "$SCRIPT_DIR/flink"

echo "Directory structure created:"
tree "$SCRIPT_DIR" 2>/dev/null || find "$SCRIPT_DIR" -type d | head -20
```

- [ ] **Step 2: 运行脚本创建目录**

```bash
chmod +x realtime/docker/setup.sh
cd realtime/docker && ./setup.sh
```

- [ ] **Step 3: 提交**

```bash
git add realtime/docker/setup.sh
git commit -m "chore: add directory setup script"
```

---

### Task 8: 验证部署

**Files:**
- No file changes - verification task

- [ ] **Step 1: 创建并运行验证脚本**

```bash
cd realtime

# 创建验证脚本
cat > docker/verify.sh << 'EOF'
#!/bin/bash
set -e

echo "=== Realtime Docker Deployment Verification ==="

# 1. 检查 Docker Compose 文件
echo -e "\n[1/6] Checking docker-compose.yml..."
docker-compose config --quiet && echo "OK: docker-compose.yml is valid"

# 2. 启动服务
echo -e "\n[2/6] Starting services..."
docker-compose up -d

# 等待服务启动
echo "Waiting 30 seconds for services to start..."
sleep 30

# 3. 检查容器状态
echo -e "\n[3/6] Checking container status..."
docker-compose ps

# 4. 验证 MySQL
echo -e "\n[4/6] Verifying MySQL..."
docker exec realtime-mysql mysqladmin ping -uroot -proot123 && echo "OK: MySQL is running"

# 5. 验证 Kafka
echo -e "\n[5/6] Verifying Kafka..."
docker exec realtime-kafka kafka-topics --list --bootstrap-server localhost:9092 && echo "OK: Kafka is running"

# 6. 验证 Canal
echo -e "\n[6/6] Verifying Canal..."
CANAL_LOGS=$(docker logs realtime-canal 2>&1 | tail -20)
if echo "$CANAL_LOGS" | grep -q "start the canal server"; then
    echo "OK: Canal server started"
else
    echo "WARN: Check Canal logs manually"
fi

echo -e "\n=== Verification Complete ==="
echo "Flink Web UI: http://localhost:8081"
echo "MySQL: localhost:3306"
echo "Kafka: localhost:9092"
echo "Canal: localhost:11111"
EOF

chmod +x docker/verify.sh
```

- [ ] **Step 2: 运行验证（需要用户确认 Docker 可用）**

```bash
cd realtime/docker && ./verify.sh
```

Expected output:
```
=== Realtime Docker Deployment Verification ===
[1/6] Checking docker-compose.yml...
OK: docker-compose.yml is valid
...
=== Verification Complete ===
```

- [ ] **Step 3: 提交验证脚本**

```bash
git add realtime/docker/verify.sh
git commit -m "chore: add deployment verification script"
```

---

## Self-Review

### 1. Spec Coverage Check

| Spec Requirement | Task |
|------------------|------|
| Docker Compose 编排 | Task 1 |
| MySQL + binlog 配置 | Task 2, Task 3 |
| Kafka KRaft（无 Zookeeper）| Task 1 |
| Canal 配置 | Task 4 |
| Flink Standalone | Task 1, Task 5 |
| 内存限制 | Task 1（docker-compose.yml 中已配置）|
| 部署说明文档 | Task 6 |

### 2. Placeholder Scan

- 无 "TBD", "TODO" 占位符
- 无 "add appropriate error handling" 等模糊描述
- 所有步骤都有具体的代码/命令

### 3. Type Consistency

- 容器命名一致：`realtime-mysql`, `realtime-kafka`, `realtime-canal`, `realtime-flink-*`
- 网络名称一致：`realtime-net`
- 端口一致：MySQL(3306), Kafka(9092), Canal(11111), Flink(8081)
- 内存限制一致：MySQL(512m), Kafka(1g), Canal(512m), Flink(1g)

---

Plan complete and saved to `docs/superpowers/plans/2026-04-16-realtime-docker-deploy.md`.

Two execution options:

**1. Subagent-Driven (recommended)** - I dispatch a fresh subagent per task, review between tasks, fast iteration

**2. Inline Execution** - Execute tasks in this session using executing-plans, batch execution with checkpoints

Which approach?
