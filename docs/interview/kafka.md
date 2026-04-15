#### 
1. 消息
2. topic 主题
3. 分区
4. 


#### 数据一致性
1. 生产者
主要通过acks参数保证
- acks=0 最大吞吐量，不保证消息一定会被写入
- acks=1 消息写入分区，leader节点会正确响应，写入失败leader节点会响应失败，
- acks=-1 消息需等待ISR所有副本写入成功后响应，可靠性最高。
2. borker
kafka服务是分布式、多分区，多副本的
3. 消费者
首先消费者在接收消息、处理消息、提交offset，可以异步执行，对于实时数据系统来说，消费端统一使用flink来开发，借助flinke的checkpoint的机制实现数据不丢失
通过设置checkpoint时间每个一段时间做一次checkpoint，持久化记录当前的source状态和kafka offset


#### 