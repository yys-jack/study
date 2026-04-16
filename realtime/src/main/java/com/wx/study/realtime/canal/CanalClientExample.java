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
            System.err.println("Canal 客户端异常：" + e.getMessage());
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
                throw new RuntimeException("解析 binlog 事件失败：" + entry, e);
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
