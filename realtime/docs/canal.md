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
