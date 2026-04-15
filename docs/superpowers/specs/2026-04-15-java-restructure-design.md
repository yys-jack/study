# Java 代码重组方案设计

**日期**: 2026-04-15  
**状态**: 待审核

---

## 1. 背景与目标

### 1.1 现状分析

当前项目是一个多模块 Gradle 项目，Java 代码分散在以下目录中：

| 模块 | 文件数估算 | 包名前缀 |
|------|----------|---------|
| `algorithm/` | ~150 | `com.wx.algorithm.*`, `com.wx.leetcode.*`, `com.wx.sort.*` |
| `juc/` | ~50 | `com.wx.threadtest.*`, `com.wx.executor.*`, `com.wx.lock.*` |
| `jvm/` | ~15 | `com.wx.jvm.*` |
| `design-patterns/` | ~30 | `com.wx.strategy.*`, `com.wx.adapter.*`, `com.wx.creationmode.*` |
| `java8/` | ~30 | `com.wx.proxy.*`, `com.wx.reflection.*`, `com.wx.io.*` |
| `priority/` | ~15 | `com.wx.priority.*` |
| `realtime/*/` | ~5 | `com.wxli.kafka.*`, `com.wxli.flink.*` |
| `common/`, `demo/` | ~5 | `com.wxli.*` |

**问题**:
- 代码分散在多个模块中，查找不便
- 包名不统一，无法通过包名快速定位代码领域
- 多模块结构对于学习项目来说过于复杂

### 1.2 重组目标

1. **统一目录结构** - 将所有 Java 源码整合到单一的 `src/main/java` 目录
2. **按领域分类** - 代码按功能领域归类，而非按模块
3. **统一包名** - 所有包名改为 `com.wx.study.{domain}.*` 格式
4. **简化构建** - 从多模块项目转为单模块 Gradle 项目

---

## 2. 领域分类方案

### 2.1 分类总览

共划分 **8 个领域**：

| 领域目录 | 包名 | 来源模块 | 内容说明 |
|---------|------|---------|---------|
| `algorithm/` | `com.wx.study.algorithm.*` | `algorithm` | 算法题、数据结构、排序 |
| `concurrent/` | `com.wx.study.concurrent.*` | `juc` | 并发编程、线程池、锁、AQS |
| `jvm/` | `com.wx.study.jvm.*` | `jvm` | JVM 类加载、内存模型、GC |
| `patterns/` | `com.wx.study.patterns.*` | `design-patterns` | 设计模式（创建型/行为型/结构型） |
| `io/` | `com.wx.study.io.*` | `java8/io` | IO、NIO、文件操作 |
| `reflection/` | `com.wx.study.reflection.*` | `java8/reflection, java8/proxy` | 反射、动态代理（JDK/CGLIB） |
| `streaming/` | `com.wx.study.streaming.*` | `realtime/*` | Kafka、Flink、Canal 流处理 |
| `framework/` | `com.wx.study.framework.*` | `priority` | 优先级任务框架 |
| `util/` | `com.wx.study.util.*` | `java8/其他` | 工具类（集合、比较、正则、克隆） |

### 2.2 详细映射关系

#### algorithm
```
来源：algorithm/src/main/java/com/wx/
目标：src/main/java/com/wx/study/algorithm/

子分类：
├── leetcode/          # LeetCode 题目
├── niuke/             # 牛客网题目
├── sort/              # 排序算法
├── datastructure/     # 数据结构（图、树、栈、队列）
├── search/            # 搜索算法（BFS、DFS）
└── introduction4/     # 《算法导论》第 4 版习题
```

#### concurrent
```
来源：juc/src/main/java/com/wx/
目标：src/main/java/com/wx/study/concurrent/

子分类：
├── thread/            # 线程基础
├── executor/          # 线程池
├── lock/              # 锁机制
├── aqs/               # AQS（CountDownLatch/CyclicBarrier/Semaphore）
├── cas/               # CAS 原子操作
├── juc-collections/   # 并发容器
├── threadlocal/       # ThreadLocal
└── interview/         # 并发面试题
```

#### jvm
```
来源：jvm/src/main/java/com/wx/
目标：src/main/java/com/wx/study/jvm/

子分类：
├── classload/         # 类加载
├── memory/            # 内存模型与溢出
├── gc/                # 垃圾回收
├── reference/         # 引用类型
└── object/            # 对象相关
```

#### patterns
```
来源：design-patterns/src/main/java/com/wx/
目标：src/main/java/com/wx/study/patterns/

子分类：
├── creational/        # 创建型模式（单例、工厂）
├── structural/        # 结构型模式（适配器）
└── behavioral/        # 行为型模式（策略、迭代器）
```

#### io
```
来源：java8/src/main/java/com/wx/io, java8/src/main/java/com/wx/file
目标：src/main/java/com/wx/study/io/

子分类：
├── nio/               # NIO 相关
└── file/              # 文件操作
```

#### reflection
```
来源：java8/src/main/java/com/wx/reflection, java8/src/main/java/com/wx/proxy
目标：src/main/java/com/wx/study/reflection/

子分类：
├── basic/             # 反射基础
├── annotation/        # 注解
├── jdk-proxy/         # JDK 动态代理
└── cglib-proxy/       # CGLIB 代理
```

#### streaming
```
来源：realtime/kafka, realtime/flink, realtime/canal
目标：src/main/java/com/wx/study/streaming/

子分类：
├── kafka/             # Kafka 生产者和消费者
├── flink/             # Flink 流处理
└── canal/             # Canal 数据同步
```

#### framework
```
来源：priority/src/main/java/com/wx/priority/
目标：src/main/java/com/wx/study/framework/priority/
```

#### util
```
来源：java8/src/main/java/com/wx/clone, collection, compare, regex
目标：src/main/java/com/wx/study/util/

子分类：
├── collection/        # 集合工具
├── compare/           # 比较工具
├── regex/             # 正则表达式
└── clone/             # 克隆
```

---

## 3. 测试文件重组

### 3.1 测试目录结构

测试文件按对应领域放入 `src/test/java/com/wx/study/{domain}/`：

| 原测试目录 | 新测试目录 |
|----------|----------|
| `algorithm/src/test/java/` | `src/test/java/com/wx/study/algorithm/` |
| `java8/src/test/java/` | `src/test/java/com/wx/study/reflection/, src/test/java/com/wx/study/util/` |

---

## 4. 构建配置调整

### 4.1 settings.gradle

```groovy
rootProject.name = 'study'
// 移除所有子模块 include 配置
```

### 4.2 build.gradle

保留根目录 `build.gradle`，移除各子模块的 `build.gradle` 文件。

整合各模块的特殊依赖（如果有）到主 `build.gradle`。

---

## 5. 实施步骤

### 阶段一：准备
1. 创建新的目录结构
2. 备份当前项目状态

### 阶段二：源码迁移
1. 按领域分类迁移 `src/main/java` 文件
2. 批量更新包名（`com.wx.*` → `com.wx.study.*`）
3. 更新模块间引用

### 阶段三：测试迁移
1. 迁移 `src/test/java` 文件
2. 更新测试文件的包名和导入

### 阶段四：构建清理
1. 删除旧模块目录
2. 清理 `settings.gradle`
3. 整合 `build.gradle`

### 阶段五：验证
1. 编译验证：`./gradlew build`
2. 测试验证：`./gradlew test`

---

## 6. 风险与缓解

| 风险 | 缓解措施 |
|------|---------|
| 包名更新遗漏导致编译失败 | 使用 IDE 重构功能或批量替换工具 |
| 模块间交叉引用断裂 | 迁移前分析依赖关系，按依赖顺序迁移 |
| 测试文件丢失 | 迁移前后对比文件数量 |
| Git 历史断裂 | 使用 `git mv` 保留文件历史 |

---

## 7. 排除内容

以下内容**本次重组不处理**：
- `面试/` - 面试资料保持原样
- `计算机网络/` - 文档资料
- `软考/` - 文档资料

---

## 8. 验收标准

- [ ] 所有 Java 源码位于 `src/main/java/com/wx/study/{domain}/`
- [ ] 所有测试文件位于 `src/test/java/com/wx/study/{domain}/`
- [ ] 所有包名统一为 `com.wx.study.{domain}.*` 格式
- [ ] `./gradlew build` 成功
- [ ] `./gradlew test` 全部通过
- [ ] Git 历史保留（使用 `git mv`）

---

## 9. 后续工作

阶段二（后续迭代）：
- 整合 `面试/` 目录中的 Java 相关代码
- 整理文档资料到统一结构
