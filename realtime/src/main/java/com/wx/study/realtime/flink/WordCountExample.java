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
