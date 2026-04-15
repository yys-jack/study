# Java 代码重组实施计划

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** 将分散在 8 个 Gradle 模块中的 297 个 Java 源码文件和 10 个测试文件按功能领域重组到统一的单模块结构中。

**Architecture:** 采用分阶段迁移策略：先创建新目录结构，按领域逐个迁移代码（使用 git mv 保留历史），批量更新包名和导入语句，最后清理旧模块配置。

**Tech Stack:** Java 21, Gradle 8.14.4, JUnit 5, Lombok, CGLIB, Kafka Clients

---

## 文件结构总览

### 新建目录
```
src/main/java/com/wx/study/
├── algorithm/
├── concurrent/
├── jvm/
├── patterns/
├── io/
├── reflection/
├── streaming/
├── framework/priority/
└── util/

src/test/java/com/wx/study/
├── algorithm/
├── concurrent/
├── jvm/
├── patterns/
├── io/
├── reflection/
├── util/
└── streaming/
```

### 修改的文件
- `settings.gradle` - 移除子模块配置
- `build.gradle` - 整合子模块依赖

### 删除的文件
- `algorithm/build.gradle`
- `juc/build.gradle`
- `jvm/build.gradle` (不存在)
- `design-patterns/build.gradle`
- `java8/build.gradle`
- `priority/build.gradle`
- `realtime/kafka/build.gradle`
- `realtime/flink/build.gradle`
- `realtime/canal/build.gradle`
- `common/build.gradle`
- `demo/build.gradle`

---

## 迁移依赖分析

根据代码分析，发现以下模块间依赖：

1. **algorithm 内部依赖**: `com.wx.introduction4.chapter1_3.graph.Graph` 引用 `com.wx.chapter1_3.exercise.Bag`
2. **algorithm search 包**: 引用 `com.wx.introduction4.chapter1_3.graph.Graph` 和 `com.wx.introduction4.chapter1_3.queue.Queue`
3. **java8 reflection**: `Parent.java` 引用同模块的 `com.wx.reflection.annotation.Idempotent`
4. **java8 proxy**: `JDKProxyFactory.java` 和 `SmsProxy.java` 引用 `com.wx.proxy.SmsService` 和 `com.wx.proxy.SmsServiceImpl`
5. **juc 内部**: `ThreadTest.java` 引用同模块的 callable 和 runnalbe 包
6. **priority 内部**: 模块内互相引用
7. **realtime**: 无特殊依赖

**迁移顺序**: 按依赖顺序迁移，先迁移被依赖的包。

---

## 任务分解

### Task 1: 准备工作 - 创建新目录结构并备份

**Files:**
- Create: `src/main/java/com/wx/study/algorithm/`
- Create: `src/main/java/com/wx/study/concurrent/`
- Create: `src/main/java/com/wx/study/jvm/`
- Create: `src/main/java/com/wx/study/patterns/`
- Create: `src/main/java/com/wx/study/io/`
- Create: `src/main/java/com/wx/study/reflection/`
- Create: `src/main/java/com/wx/study/streaming/`
- Create: `src/main/java/com/wx/study/framework/priority/`
- Create: `src/main/java/com/wx/study/util/`
- Create: `src/test/java/com/wx/study/algorithm/`
- Create: `src/test/java/com/wx/study/concurrent/`
- Create: `src/test/java/com/wx/study/jvm/`
- Create: `src/test/java/com/wx/study/patterns/`
- Create: `src/test/java/com/wx/study/io/`
- Create: `src/test/java/com/wx/study/reflection/`
- Create: `src/test/java/com/wx/study/util/`
- Create: `src/test/java/com/wx/study/streaming/`

- [ ] **Step 1: 创建新的源码目录结构**

```bash
mkdir -p src/main/java/com/wx/study/{algorithm,concurrent,jvm,patterns,io,reflection,streaming,framework/priority,util}
mkdir -p src/test/java/com/wx/study/{algorithm,concurrent,jvm,patterns,io,reflection,util,streaming}
```

- [ ] **Step 2: 创建当前状态分支（备份点）**

```bash
git checkout -b feature/java-restructure-backup
git checkout main
```

- [ ] **Step 3: 创建重组工作分支**

```bash
git checkout -b feature/java-restructure
```

- [ ] **Step 4: 统计当前文件数量（用于后续验证）**

```bash
echo "Main sources: $(find . -path '*/src/main/java/*.java' | grep -v build | wc -l)"
echo "Test sources: $(find . -path '*/src/test/java/*.java' | grep -v build | wc -l)"
```

Expected: Main ~297, Test ~10

---

### Task 2: 迁移 JVM 模块（无依赖）

**Files:**
- Move: `jvm/src/main/java/com/wx/*` → `src/main/java/com/wx/study/jvm/`
- Test: 无测试文件

- [ ] **Step 1: 迁移 JVM 源码文件**

```bash
# 使用 git mv 保留文件历史
git mv jvm/src/main/java/com/wx/classload src/main/java/com/wx/study/jvm/
git mv jvm/src/main/java/com/wx/class_life_cycle src/main/java/com/wx/study/jvm/
git mv jvm/src/main/java/com/wx/gc src/main/java/com/wx/study/jvm/
git mv jvm/src/main/java/com/wx/memoryoverflow src/main/java/com/wx/study/jvm/
git mv jvm/src/main/java/com/wx/object src/main/java/com/wx/study/jvm/
git mv jvm/src/main/java/com/wx/reference src/main/java/com/wx/study/jvm/
git mv jvm/src/main/java/com/wx/JvmClassLoaderPrintPath.java src/main/java/com/wx/study/jvm/
```

- [ ] **Step 2: 更新 JVM 模块的包名**

```bash
# 批量替换包名
find src/main/java/com/wx/study/jvm -name "*.java" -type f -exec sed -i 's/^package com\.wx\./package com.wx.study./g' {} \;
find src/main/java/com/wx/study/jvm -name "*.java" -type f -exec sed -i 's/^import com\.wx\./import com.wx.study./g' {} \;
```

- [ ] **Step 3: 验证一个文件的包名更新**

Read one file to verify, e.g., `src/main/java/com/wx/study/jvm/classload/ClassLoaderTest.java`:
Expected: `package com.wx.study.jvm;` or `package com.wx.study.jvm.classload;`

- [ ] **Step 4: 提交 JVM 模块迁移**

```bash
git add src/main/java/com/wx/study/jvm/
git commit -m "refactor: migrate jvm module to unified structure"
```

---

### Task 3: 迁移 Patterns 模块（设计模式，无外部依赖）

**Files:**
- Move: `design-patterns/src/main/java/com/wx/*` → `src/main/java/com/wx/study/patterns/`
- Test: 无测试文件

- [ ] **Step 1: 迁移设计模式源码**

```bash
git mv design-patterns/src/main/java/com/wx/behavioralmode src/main/java/com/wx/study/patterns/
git mv design-patterns/src/main/java/com/wx/strategy src/main/java/com/wx/study/patterns/
git mv design-patterns/src/main/java/com/wx/creationmode src/main/java/com/wx/study/patterns/
git mv design-patterns/src/main/java/com/wx/adapter src/main/java/com/wx/study/patterns/
git mv design-patterns/src/main/java/com/wx/T17_10.java src/main/java/com/wx/study/patterns/
```

- [ ] **Step 2: 更新包名**

```bash
find src/main/java/com/wx/study/patterns -name "*.java" -type f -exec sed -i 's/^package com\.wx\./package com.wx.study./g' {} \;
find src/main/java/com/wx/study/patterns -name "*.java" -type f -exec sed -i 's/^import com\.wx\./import com.wx.study./g' {} \;
```

- [ ] **Step 3: 重新组织 patterns 子目录（可选，保持清晰的模式分类）**

```bash
# 将原有包结构重命名为更清晰的分类
mkdir -p src/main/java/com/wx/study/patterns/creational
mkdir -p src/main/java/com/wx/study/patterns/structural
mkdir -p src/main/java/com/wx/study/patterns/behavioral

mv src/main/java/com/wx/study/patterns/creationmode/* src/main/java/com/wx/study/patterns/creational/ 2>/dev/null || true
mv src/main/java/com/wx/study/patterns/adapter src/main/java/com/wx/study/patterns/structural/ 2>/dev/null || true
mv src/main/java/com/wx/study/patterns/behavioralmode/* src/main/java/com/wx/study/patterns/behavioral/ 2>/dev/null || true
mv src/main/java/com/wx/study/patterns/strategy src/main/java/com/wx/study/patterns/behavioral/ 2>/dev/null || true

# 清理空目录
rmdir src/main/java/com/wx/study/patterns/creationmode 2>/dev/null || true
rmdir src/main/java/com/wx/study/patterns/behavioralmode 2>/dev/null || true
rmdir src/main/java/com/wx/study/patterns/adapter 2>/dev/null || true
rmdir src/main/java/com/wx/study/patterns/strategy 2>/dev/null || true
```

- [ ] **Step 4: 更新移动后的文件包名**

```bash
find src/main/java/com/wx/study/patterns/creational -name "*.java" -type f -exec sed -i "s/package com.wx.study.patterns.creationmode/package com.wx.study.patterns.creational/g" {} \;
find src/main/java/com/wx/study/patterns/behavioral -name "*.java" -type f -exec sed -i "s/package com.wx.study.patterns.strategy/package com.wx.study.patterns.behavioral/g" {} \;
find src/main/java/com/wx/study/patterns/behavioral -name "*.java" -type f -exec sed -i "s/package com.wx.study.patterns.behavioralmode/package com.wx.study.patterns.behavioral/g" {} \;
find src/main/java/com/wx/study/patterns/structural -name "*.java" -type f -exec sed -i "s/package com.wx.study.patterns.adapter/package com.wx.study.patterns.structural/g" {} \;
```

- [ ] **Step 5: 提交 patterns 模块迁移**

```bash
git add src/main/java/com/wx/study/patterns/
git commit -m "refactor: migrate design-patterns module to unified structure"
```

---

### Task 4: 迁移 Util 模块（java8 中的工具类）

**Files:**
- Move: `java8/src/main/java/com/wx/clone` → `src/main/java/com/wx/study/util/`
- Move: `java8/src/main/java/com/wx/collection` → `src/main/java/com/wx/study/util/`
- Move: `java8/src/main/java/com/wx/compare` → `src/main/java/com/wx/study/util/`
- Move: `java8/src/main/java/com/wx/regex` → `src/main/java/com/wx/study/util/`
- Move: `java8/src/main/java/com/wx/file` → `src/main/java/com/wx/study/io/` (归入 io 领域)
- Test: `java8/src/test/java/com/wx/clone/*` → `src/test/java/com/wx/study/util/`
- Test: `java8/src/test/java/com/wx/collection/*` → `src/test/java/com/wx/study/util/`
- Test: `java8/src/test/java/com/wx/compare/*` → `src/test/java/com/wx/study/util/`
- Test: `java8/src/test/java/com/wx/file/*` → `src/test/java/com/wx/study/io/`

- [ ] **Step 1: 迁移 util 相关文件到 io 和 util 目录**

```bash
git mv java8/src/main/java/com/wx/clone src/main/java/com/wx/study/util/
git mv java8/src/main/java/com/wx/collection src/main/java/com/wx/study/util/
git mv java8/src/main/java/com/wx/compare src/main/java/com/wx/study/util/
git mv java8/src/main/java/com/wx/regex src/main/java/com/wx/study/util/
git mv java8/src/main/java/com/wx/file src/main/java/com/wx/study/io/
```

- [ ] **Step 2: 迁移 util 相关测试文件**

```bash
git mv java8/src/test/java/com/wx/clone src/test/java/com/wx/study/util/
git mv java8/src/test/java/com/wx/collection src/test/java/com/wx/study/util/
git mv java8/src/test/java/com/wx/compare src/test/java/com/wx/study/util/
git mv java8/src/test/java/com/wx/file src/test/java/com/wx/study/io/
```

- [ ] **Step 3: 更新 util 模块包名**

```bash
find src/main/java/com/wx/study/util -name "*.java" -type f -exec sed -i 's/^package com\.wx\./package com.wx.study./g' {} \;
find src/main/java/com/wx/study/util -name "*.java" -type f -exec sed -i 's/^import com\.wx\./import com.wx.study./g' {} \;
find src/test/java/com/wx/study/util -name "*.java" -type f -exec sed -i 's/^package com\.wx\./package com.wx.study./g' {} \;
find src/test/java/com/wx/study/util -name "*.java" -type f -exec sed -i 's/^import com\.wx\./import com.wx.study./g' {} \;
```

- [ ] **Step 4: 更新 io 模块包名（file 目录）**

```bash
find src/main/java/com/wx/study/io -name "*.java" -type f -exec sed -i 's/^package com\.wx\./package com.wx.study./g' {} \;
find src/main/java/com/wx/study/io -name "*.java" -type f -exec sed -i 's/^import com\.wx\./import com.wx.study./g' {} \;
find src/test/java/com/wx/study/io -name "*.java" -type f -exec sed -i 's/^package com\.wx\./package com.wx.study./g' {} \;
find src/test/java/com/wx/study/io -name "*.java" -type f -exec sed -i 's/^import com\.wx\./import com.wx.study./g' {} \;
```

- [ ] **Step 5: 提交 util 和 io 模块迁移**

```bash
git add src/main/java/com/wx/study/util/ src/main/java/com/wx/study/io/ src/test/java/com/wx/study/util/ src/test/java/com/wx/study/io/
git commit -m "refactor: migrate util and io modules to unified structure"
```

---

### Task 5: 迁移 Reflection 模块（反射和代理）

**Files:**
- Move: `java8/src/main/java/com/wx/reflection` → `src/main/java/com/wx/study/reflection/`
- Move: `java8/src/main/java/com/wx/proxy` → `src/main/java/com/wx/study/reflection/`
- Test: `java8/src/test/java/com/wx/reflection/*` → `src/test/java/com/wx/study/reflection/`
- Test: `java8/src/test/java/com/wx/fun/*` → `src/test/java/com/wx/study/util/` (Optional 相关归入 util)

- [ ] **Step 1: 迁移 reflection 和 proxy 源码**

```bash
git mv java8/src/main/java/com/wx/reflection src/main/java/com/wx/study/reflection/
git mv java8/src/main/java/com/wx/proxy src/main/java/com/wx/study/reflection/
```

- [ ] **Step 2: 迁移 reflection 测试文件**

```bash
git mv java8/src/test/java/com/wx/reflection src/test/java/com/wx/study/reflection/
git mv java8/src/test/java/com/wx/fun src/test/java/com/wx/study/util/
```

- [ ] **Step 3: 更新 reflection 模块包名**

```bash
# 更新 reflection 包
find src/main/java/com/wx/study/reflection -name "*.java" -type f -exec sed -i 's/^package com\.wx\./package com.wx.study./g' {} \;
find src/main/java/com/wx/study/reflection -name "*.java" -type f -exec sed -i 's/^import com\.wx\./import com.wx.study./g' {} \;
find src/test/java/com/wx/study/reflection -name "*.java" -type f -exec sed -i 's/^package com\.wx\./package com.wx.study./g' {} \;
find src/test/java/com/wx/study/reflection -name "*.java" -type f -exec sed -i 's/^import com\.wx\./import com.wx.study./g' {} \;
```

- [ ] **Step 4: 重新组织 reflection 子目录**

```bash
# 将 proxy 相关的包名更新为 reflection.proxy
find src/main/java/com/wx/study/reflection/proxy -name "*.java" -type f -exec sed -i 's/package com.wx.study.reflection.proxy/package com.wx.study.reflection.proxy/g' {} \;
# 确保 proxy 导入 reflection 的包正确
find src/main/java/com/wx/study/reflection/proxy -name "*.java" -type f -exec sed -i 's/import com.wx.study.SmsService/import com.wx.study.reflection.SmsService/g' {} \;
```

- [ ] **Step 5: 提交 reflection 模块迁移**

```bash
git add src/main/java/com/wx/study/reflection/ src/test/java/com/wx/study/reflection/ src/test/java/com/wx/study/util/
git commit -m "refactor: migrate reflection and proxy modules to unified structure"
```

---

### Task 6: 迁移 Concurrent 模块（JUC 并发）

**Files:**
- Move: `juc/src/main/java/com/wx/*` → `src/main/java/com/wx/study/concurrent/`
- Test: 无测试文件

- [ ] **Step 1: 迁移 concurrent 源码**

```bash
git mv juc/src/main/java/com/wx/aqs src/main/java/com/wx/study/concurrent/
git mv juc/src/main/java/com/wx/cas src/main/java/com/wx/study/concurrent/
git mv juc/src/main/java/com/wx/container src/main/java/com/wx/study/concurrent/
git mv juc/src/main/java/com/wx/escape src/main/java/com/wx/study/concurrent/
git mv juc/src/main/java/com/wx/executor src/main/java/com/wx/study/concurrent/
git mv juc/src/main/java/com/wx/interview src/main/java/com/wx/study/concurrent/
git mv juc/src/main/java/com/wx/lock src/main/java/com/wx/study/concurrent/
git mv juc/src/main/java/com/wx/producerconsumermodel src/main/java/com/wx/study/concurrent/
git mv juc/src/main/java/com/wx/threadlocal src/main/java/com/wx/study/concurrent/
git mv juc/src/main/java/com/wx/threadtest src/main/java/com/wx/study/concurrent/
git mv juc/src/main/java/com/wx/unsafe src/main/java/com/wx/study/concurrent/
git mv juc/src/main/java/com/wx/waitnotify src/main/java/com/wx/study/concurrent/
```

- [ ] **Step 2: 更新 concurrent 模块包名**

```bash
find src/main/java/com/wx/study/concurrent -name "*.java" -type f -exec sed -i 's/^package com\.wx\./package com.wx.study./g' {} \;
find src/main/java/com/wx/study/concurrent -name "*.java" -type f -exec sed -i 's/^import com\.wx\./import com.wx.study./g' {} \;
```

- [ ] **Step 3: 提交 concurrent 模块迁移**

```bash
git add src/main/java/com/wx/study/concurrent/
git commit -m "refactor: migrate juc (concurrent) module to unified structure"
```

---

### Task 7: 迁移 Streaming 模块（Kafka, Flink, Canal）

**Files:**
- Move: `realtime/kafka/src/main/java/com/wxli/*` → `src/main/java/com/wx/study/streaming/kafka/`
- Move: `realtime/flink/src/main/java/com/wxli/*` → `src/main/java/com/wx/study/streaming/flink/`
- Move: `realtime/canal/src/main/java/com/wxli/*` → `src/main/java/com/wx/study/streaming/canal/`
- Test: 无测试文件

- [ ] **Step 1: 迁移 streaming 源码**

```bash
git mv realtime/kafka/src/main/java/com/wxli/KafkaConsumerDemo.java src/main/java/com/wx/study/streaming/kafka/
git mv realtime/kafka/src/main/java/com/wxli/KafkaProducerDemo.java src/main/java/com/wx/study/streaming/kafka/
git mv realtime/flink/src/main/java/com/wxli/WordCountApp.java src/main/java/com/wx/study/streaming/flink/
git mv realtime/canal/src/main/java/com/wxli/SimpleCanalClientExample.java src/main/java/com/wx/study/streaming/canal/
```

注意：如果目录不存在，先创建：
```bash
mkdir -p src/main/java/com/wx/study/streaming/{kafka,flink,canal}
```

- [ ] **Step 2: 更新 streaming 模块包名**

```bash
find src/main/java/com/wx/study/streaming -name "*.java" -type f -exec sed -i 's/^package com.wxli\./package com.wx.study./g' {} \;
find src/main/java/com/wx/study/streaming -name "*.java" -type f -exec sed -i 's/^import com.wxli\./import com.wx.study./g' {} \;
```

- [ ] **Step 3: 提交 streaming 模块迁移**

```bash
git add src/main/java/com/wx/study/streaming/
git commit -m "refactor: migrate realtime (streaming) module to unified structure"
```

---

### Task 8: 迁移 Framework 模块（Priority 优先级框架）

**Files:**
- Move: `priority/src/main/java/com/wx/priority/*` → `src/main/java/com/wx/study/framework/priority/`
- Test: 无测试文件

- [ ] **Step 1: 迁移 framework/priority 源码**

```bash
git mv priority/src/main/java/com/wx/priority/evaluator src/main/java/com/wx/study/framework/priority/
git mv priority/src/main/java/com/wx/priority/message src/main/java/com/wx/study/framework/priority/
git mv priority/src/main/java/com/wx/priority/task src/main/java/com/wx/study/framework/priority/
git mv priority/src/main/java/com/wx/priority/TaskExecutor.java src/main/java/com/wx/study/framework/priority/
```

- [ ] **Step 2: 更新 framework 模块包名**

```bash
find src/main/java/com/wx/study/framework -name "*.java" -type f -exec sed -i 's/^package com\.wx\.priority\./package com.wx.study.framework./g' {} \;
find src/main/java/com/wx/study/framework -name "*.java" -type f -exec sed -i 's/^import com\.wx\.priority\./import com.wx.study.framework./g' {} \;
```

- [ ] **Step 3: 提交 framework 模块迁移**

```bash
git add src/main/java/com/wx/study/framework/
git commit -m "refactor: migrate priority (framework) module to unified structure"
```

---

### Task 9: 迁移 Algorithm 模块（最复杂，有内部依赖）

**Files:**
- Move: `algorithm/src/main/java/com/wx/*` → `src/main/java/com/wx/study/algorithm/`
- Test: `algorithm/src/test/java/com/wx/*` → `src/test/java/com/wx/study/algorithm/`

**特殊处理**: algorithm 模块内部有交叉引用：
- `com.wx.introduction4.chapter1_3.graph.Graph` 引用 `com.wx.chapter1_3.exercise.Bag`
- `com.wx.search.*` 引用 `com.wx.introduction4.chapter1_3.graph.Graph` 和 `com.wx.introduction4.chapter1_3.queue.Queue`

- [ ] **Step 1: 迁移 algorithm 源码**

```bash
git mv algorithm/src/main/java/com/wx/introduction4 src/main/java/com/wx/study/algorithm/
git mv algorithm/src/main/java/com/wx/leetcode src/main/java/com/wx/study/algorithm/
git mv algorithm/src/main/java/com/wx/niuke src/main/java/com/wx/study/algorithm/
git mv algorithm/src/main/java/com/wx/sort src/main/java/com/wx/study/algorithm/
git mv algorithm/src/main/java/com/wx/search src/main/java/com/wx/study/algorithm/
git mv algorithm/src/main/java/com/wx/string src/main/java/com/wx/study/algorithm/
```

- [ ] **Step 2: 迁移 algorithm 测试**

```bash
git mv algorithm/src/test/java/com/wx/MatrixMultiplicationTest.java src/test/java/com/wx/study/algorithm/
```

- [ ] **Step 3: 更新 algorithm 模块包名（第一遍：基础替换）**

```bash
find src/main/java/com/wx/study/algorithm -name "*.java" -type f -exec sed -i 's/^package com\.wx\./package com.wx.study./g' {} \;
find src/main/java/com/wx/study/algorithm -name "*.java" -type f -exec sed -i 's/^import com\.wx\./import com.wx.study./g' {} \;
find src/test/java/com/wx/study/algorithm -name "*.java" -type f -exec sed -i 's/^package com\.wx\./package com.wx.study./g' {} \;
find src/test/java/com/wx/study/algorithm -name "*.java" -type f -exec sed -i 's/^import com\.wx\./import com.wx.study./g' {} \;
```

- [ ] **Step 4: 修复 introduction4 内部引用（Bag 的引用）**

检查 `src/main/java/com/wx/study/algorithm/introduction4/chapter1_3/graph/Graph.java`:
Expected import: `import com.wx.study.algorithm.introduction4.chapter1_3.exercise.Bag;`

如果引用断裂，手动修复：
```bash
# 检查 Bag 的位置
ls src/main/java/com/wx/study/algorithm/introduction4/chapter1_3/exercise/
# 如果没有 exercise 目录，需要调整结构
```

- [ ] **Step 5: 提交 algorithm 模块迁移**

```bash
git add src/main/java/com/wx/study/algorithm/ src/test/java/com/wx/study/algorithm/
git commit -m "refactor: migrate algorithm module to unified structure"
```

---

### Task 10: 迁移 Common 和 Demo 模块

**Files:**
- Move: `common/src/main/java/*` → `src/main/java/com/wx/study/util/` (如果有内容)
- Move: `demo/src/main/java/com/wxli/Main.java` → `src/main/java/com/wx/study/util/` 或 `src/main/java/com/wx/study/demo/`

- [ ] **Step 1: 检查 common 模块内容**

```bash
find common/src -name "*.java" 2>/dev/null
```

如果有内容则迁移，否则跳过。

- [ ] **Step 2: 迁移 demo 模块**

```bash
# 创建 demo 目录
mkdir -p src/main/java/com/wx/study/demo
git mv demo/src/main/java/com/wxli/Main.java src/main/java/com/wx/study/demo/
```

- [ ] **Step 3: 更新 demo 模块包名**

```bash
sed -i 's/^package com.wxli\./package com.wx.study./g' src/main/java/com/wx/study/demo/Main.java
sed -i 's/^import com.wxli\./import com.wx.study./g' src/main/java/com/wx/study/demo/Main.java
```

- [ ] **Step 4: 提交 common/demo 迁移**

```bash
git add src/main/java/com/wx/study/demo/
git commit -m "refactor: migrate demo module to unified structure"
```

---

### Task 11: 清理旧模块目录和构建配置

**Files:**
- Modify: `settings.gradle`
- Modify: `build.gradle`
- Delete: 所有子模块的 `build.gradle`
- Delete: 旧模块目录（`jvm/`, `juc/`, `design-patterns/`, `java8/`, `priority/`, `realtime/`, `algorithm/`, `common/`, `demo/`）

- [ ] **Step 1: 更新 settings.gradle**

```bash
cat > settings.gradle << 'EOF'
rootProject.name = 'study'
EOF
```

- [ ] **Step 2: 整合 build.gradle 依赖**

读取当前 `build.gradle`，然后添加子模块的特殊依赖：

```groovy
allprojects {
    group = 'com.wxli'
    version = '1.0.0'
}

subprojects {
    apply plugin: 'java'
    apply plugin: 'idea'

    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21

    dependencies {
        implementation 'ch.qos.logback:logback-classic:1.5.6'
        testImplementation 'org.junit.jupiter:junit-jupiter:5.10.2'
        testImplementation 'org.assertj:assertj-core:3.26.0'
        
        // 从各子模块整合的依赖
        compileOnly 'org.projectlombok:lombok'
        implementation 'cglib:cglib:3.3.0'
        implementation 'org.apache.kafka:kafka-clients:3.8.0'
    }

    repositories {
        mavenLocal()
        mavenCentral()
        gradlePluginPortal()
    }

    tasks.withType(JavaCompile) {
        options.encoding = 'UTF-8'
        options.release = 21
    }

    test {
        useJUnitPlatform()
        testLogging {
            events "passed", "skipped", "failed"
        }
    }
}
```

注意：由于现在是单模块项目，需要调整 `subprojects` 为直接配置：

```groovy
allprojects {
    group = 'com.wxli'
    version = '1.0.0'
}

apply plugin: 'java'
apply plugin: 'idea'

sourceCompatibility = JavaVersion.VERSION_21
targetCompatibility = JavaVersion.VERSION_21

dependencies {
    implementation 'ch.qos.logback:logback-classic:1.5.6'
    testImplementation 'org.junit.jupiter:junit-jupiter:5.10.2'
    testImplementation 'org.assertj:assertj-core:3.26.0'
    
    compileOnly 'org.projectlombok:lombok'
    implementation 'cglib:cglib:3.3.0'
    implementation 'org.apache.kafka:kafka-clients:3.8.0'
}

repositories {
    mavenLocal()
    mavenCentral()
    gradlePluginPortal()
}

tasks.withType(JavaCompile) {
    options.encoding = 'UTF-8'
    options.release = 21
}

test {
    useJUnitPlatform()
    testLogging {
        events "passed", "skipped", "failed"
    }
}
```

- [ ] **Step 3: 删除子模块 build.gradle 文件**

```bash
rm -f algorithm/build.gradle
rm -f juc/build.gradle
rm -f jvm/build.gradle 2>/dev/null || true
rm -f design-patterns/build.gradle
rm -f java8/build.gradle
rm -f priority/build.gradle
rm -f realtime/kafka/build.gradle
rm -f realtime/flink/build.gradle
rm -f realtime/canal/build.gradle
rm -f common/build.gradle
rm -f demo/build.gradle
```

- [ ] **Step 4: 删除空模块目录**

```bash
# 先检查目录是否为空或只包含非源码文件
rmdir algorithm/src/main/java 2>/dev/null || true
rmdir algorithm/src/test/java 2>/dev/null || true
rmdir algorithm/src 2>/dev/null || true
# 对其他模块重复此操作...
```

更安全的方式是先移动整个模块目录到临时位置，验证构建成功后再删除。

- [ ] **Step 5: 提交构建配置变更**

```bash
git add settings.gradle build.gradle
git commit -m "refactor: consolidate multi-module build config to single module"
```

---

### Task 12: 构建验证

- [ ] **Step 1: 清理 Gradle 缓存**

```bash
./gradlew clean
```

- [ ] **Step 2: 编译验证**

```bash
./gradlew compileJava
```

Expected: BUILD SUCCESSFUL

- [ ] **Step 3: 如有编译错误，修复包引用问题**

常见错误及修复：
- "cannot find symbol" - 检查 import 语句是否已更新
- "package does not exist" - 检查包名是否一致

- [ ] **Step 4: 测试验证**

```bash
./gradlew test
```

Expected: BUILD SUCCESSFUL, all tests passed

- [ ] **Step 5: 完整构建验证**

```bash
./gradlew build
```

- [ ] **Step 6: 提交验证通过的代码**

```bash
git add .
git commit -m "chore: verify build after restructuring"
```

---

### Task 13: 最终验证和清理

- [ ] **Step 1: 统计新结构的文件数量**

```bash
echo "Main sources: $(find src/main/java -name '*.java' | wc -l)"
echo "Test sources: $(find src/test/java -name '*.java' | wc -l)"
```

Expected: Main ~297, Test ~10（与迁移前一致）

- [ ] **Step 2: 验证 Git 历史保留**

```bash
git log --follow src/main/java/com/wx/study/jvm/classload/ClassLoaderTest.java | head -10
```

Expected: 应能看到该文件在原始模块中的提交历史

- [ ] **Step 3: 删除旧的模块目录（如果之前只是移动了内容）**

```bash
# 检查旧目录是否还有内容
ls -la algorithm/ 2>/dev/null
# 如果为空或只有空目录结构，可以删除
rm -rf algorithm/ 2>/dev/null || true
rm -rf juc/ 2>/dev/null || true
rm -rf jvm/ 2>/dev/null || true
rm -rf design-patterns/ 2>/dev/null || true
rm -rf java8/ 2>/dev/null || true
rm -rf priority/ 2>/dev/null || true
rm -rf realtime/ 2>/dev/null || true
rm -rf common/ 2>/dev/null || true
rm -rf demo/ 2>/dev/null || true
```

- [ ] **Step 4: 提交最终清理**

```bash
git add -A
git commit -m "chore: remove old module directories after successful migration"
```

---

### Task 14: 验收标准检查

- [ ] **Step 1: 检查验收标准**

对照设计文档中的验收标准逐一检查：

```bash
# 1. 所有 Java 源码位于 src/main/java/com/wx/study/{domain}/
find src/main/java/com/wx/study -name "*.java" | head -5

# 2. 所有测试文件位于 src/test/java/com/wx/study/{domain}/
find src/test/java/com/wx/study -name "*.java"

# 3. 所有包名统一为 com.wx.study.{domain}.* 格式
grep -r "^package com.wx.study" src/main/java | head -5

# 4. 构建成功
./gradlew build

# 5. 测试全部通过
./gradlew test
```

- [ ] **Step 2: 创建 PR 或合并到主分支**

```bash
git checkout main
git merge feature/java-restructure
```

- [ ] **Step 3: 清理特性分支（可选）**

```bash
git branch -d feature/java-restructure-backup
```

---

## 依赖和特殊说明

### 包名映射速查表

| 原包名 | 新包名 |
|-------|-------|
| `com.wx.jvm.*` | `com.wx.study.jvm.*` |
| `com.wx.threadtest.*` | `com.wx.study.concurrent.*` |
| `com.wx.strategy.*` | `com.wx.study.patterns.*` |
| `com.wx.adapter.*` | `com.wx.study.patterns.*` |
| `com.wx.creationmode.*` | `com.wx.study.patterns.creational.*` |
| `com.wx.proxy.*` | `com.wx.study.reflection.*` |
| `com.wx.reflection.*` | `com.wx.study.reflection.*` |
| `com.wx.priority.*` | `com.wx.study.framework.priority.*` |
| `com.wxli.*` | `com.wx.study.*` |
| `com.wx.leetcode.*` | `com.wx.study.algorithm.*` |
| `com.wx.sort.*` | `com.wx.study.algorithm.*` |

### 常见问题处理

**Q: sed 替换后包名和目录不匹配？**
A: 目录结构已在 git mv 时处理好，sed 只更新文件内容。

**Q: 编译时出现循环依赖？**
A: 检查 algorithm 模块内部的 introduction4 包结构，确保 Bag 和 Graph 的引用正确。

**Q: 测试类找不到被测试类？**
A: 确保测试文件的 import 语句已更新为新的包名。
