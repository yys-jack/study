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
