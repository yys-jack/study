# Java Module Split Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** 将 src 目录下的 Java 代码拆分为 7 个独立的 Gradle 子模块（core、concurrent、patterns、io、algorithm、framework、demo）

**Architecture:** 创建 `modules/` 目录存放所有子模块，core 模块包含 jvm+reflection+util，其他模块独立，模块间通过 Gradle 依赖管理。

**Tech Stack:** Java 21, Gradle 8.x

---

## File Structure

### 新增文件

| 文件路径 | 说明 |
|---------|------|
| `modules/core/build.gradle` | core 模块构建配置 |
| `modules/concurrent/build.gradle` | concurrent 模块构建配置 |
| `modules/patterns/build.gradle` | patterns 模块构建配置 |
| `modules/io/build.gradle` | io 模块构建配置 |
| `modules/algorithm/build.gradle` | algorithm 模块构建配置 |
| `modules/framework/build.gradle` | framework 模块构建配置 |
| `modules/demo/build.gradle` | demo 模块构建配置 |

### 修改文件

| 文件路径 | 修改内容 |
|---------|---------|
| `build.gradle` | 改为根项目配置，添加 subprojects 块 |
| `settings.gradle` | 添加 7 个新模块 |

### 迁移文件

| 源目录 | 目标目录 | 包名变更 |
|-------|---------|---------|
| `src/main/java/com/wx/study/jvm/` | `modules/core/src/main/java/com/wx/study/core/jvm/` | `com.wx.study.jvm.*` → `com.wx.study.core.jvm.*` |
| `src/main/java/com/wx/study/reflection/` | `modules/core/src/main/java/com/wx/study/core/reflection/` | `com.wx.study.reflection.*` → `com.wx.study.core.reflection.*` |
| `src/main/java/com/wx/study/util/` | `modules/core/src/main/java/com/wx/study/core/util/` | `com.wx.study.util.*` → `com.wx.study.core.util.*` |
| `src/main/java/com/wx/study/concurrent/` | `modules/concurrent/src/main/java/com/wx/study/concurrent/` | 不变 |
| `src/main/java/com/wx/study/patterns/` | `modules/patterns/src/main/java/com/wx/study/patterns/` | 不变 |
| `src/main/java/com/wx/study/io/` | `modules/io/src/main/java/com/wx/study/io/` | 不变 |
| `src/main/java/com/wx/study/algorithm/` | `modules/algorithm/src/main/java/com/wx/study/algorithm/` | 不变 |
| `src/main/java/com/wx/study/framework/` | `modules/framework/src/main/java/com/wx/study/framework/` | 不变 |
| `src/main/java/com/wx/study/demo/` | `modules/demo/src/main/java/com/wx/study/demo/` | 不变 |

---

## Task 1: 更新根项目构建配置

**Files:**
- Modify: `build.gradle`

- [ ] **Step 1: 备份并重写 build.gradle**

```groovy
allprojects {
    group = 'com.wx.study'
    version = '1.0.0'
}

subprojects {
    apply plugin: 'java'
    
    java {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    
    repositories {
        mavenLocal()
        mavenCentral()
        gradlePluginPortal()
    }
    
    // 共享依赖
    dependencies {
        implementation 'ch.qos.logback:logback-classic:1.5.6'
        compileOnly 'org.projectlombok:lombok:1.18.32'
        testImplementation 'org.junit.jupiter:junit-jupiter:5.10.2'
        testImplementation 'org.assertj:assertj-core:3.26.0'
    }
    
    test {
        useJUnitPlatform()
        testLogging {
            events "passed", "skipped", "failed"
        }
    }
}
```

- [ ] **Step 2: 验证配置**

```bash
gradle projects
```

Expected: 显示所有子模块（包括 realtime）

- [ ] **Step 3: 提交**

```bash
git add build.gradle
git commit -m "build: configure root project for multi-module build"
```

---

## Task 2: 更新 settings.gradle 注册新模块

**Files:**
- Modify: `settings.gradle`

- [ ] **Step 1: 更新 settings.gradle**

```groovy
rootProject.name = 'study'

// 已有模块
include ':realtime'

// 新增模块
include ':modules:core'
include ':modules:concurrent'
include ':modules:patterns'
include ':modules:io'
include ':modules:algorithm'
include ':modules:framework'
include ':modules:demo'
```

- [ ] **Step 2: 验证模块注册**

```bash
gradle projects
```

Expected: 显示 8 个子模块

- [ ] **Step 3: 提交**

```bash
git add settings.gradle
git commit -m "build: register new modules in settings"
```

---

## Task 3: 创建 core 模块

**Files:**
- Create: `modules/core/build.gradle`
- Create: `modules/core/src/main/java/com/wx/study/core/jvm/`
- Create: `modules/core/src/main/java/com/wx/study/core/reflection/`
- Create: `modules/core/src/main/java/com/wx/study/core/util/`

- [ ] **Step 1: 创建目录结构**

```bash
mkdir -p modules/core/src/main/java/com/wx/study/core
mkdir -p modules/core/src/test/java
```

- [ ] **Step 2: 创建 build.gradle**

```groovy
dependencies {
    implementation 'org.openjdk.jol:jol-core:0.17'
    implementation 'cglib:cglib:3.3.0'
}
```

- [ ] **Step 3: 迁移 jvm 代码**

```bash
cp -r src/main/java/com/wx/study/jvm modules/core/src/main/java/com/wx/study/core/
```

- [ ] **Step 4: 迁移 reflection 代码**

```bash
cp -r src/main/java/com/wx/study/reflection modules/core/src/main/java/com/wx/study/core/
```

- [ ] **Step 5: 迁移 util 代码**

```bash
cp -r src/main/java/com/wx/study/util modules/core/src/main/java/com/wx/study/core/
```

- [ ] **Step 6: 批量更新包名 - jvm**

```bash
find modules/core/src/main/java/com/wx/study/core/jvm -name "*.java" -exec sed -i 's/package com\.wx\.study\.jvm\./package com.wx.study.core.jvm./g' {} \;
find modules/core/src/main/java/com/wx/study/core/jvm -name "*.java" -exec sed -i 's/import com\.wx\.study\.jvm\./import com.wx.study.core.jvm./g' {} \;
```

- [ ] **Step 7: 批量更新包名 - reflection**

```bash
find modules/core/src/main/java/com/wx/study/core/reflection -name "*.java" -exec sed -i 's/package com\.wx\.study\.reflection\./package com.wx.study.core.reflection./g' {} \;
find modules/core/src/main/java/com/wx/study/core/reflection -name "*.java" -exec sed -i 's/import com\.wx\.study\.reflection\./import com.wx.study.core.reflection./g' {} \;
```

- [ ] **Step 8: 批量更新包名 - util**

```bash
find modules/core/src/main/java/com/wx/study/core/util -name "*.java" -exec sed -i 's/package com\.wx\.study\.util\./package com.wx.study.core.util./g' {} \;
find modules/core/src/main/java/com/wx/study/core/util -name "*.java" -exec sed -i 's/import com\.wx\.study\.util\./import com.wx.study.core.util./g' {} \;
```

- [ ] **Step 9: 验证 core 模块编译**

```bash
gradle :modules:core:compileJava
```

Expected: BUILD SUCCESSFUL

- [ ] **Step 10: 提交**

```bash
git add modules/core/
git commit -m "feat: create core module with jvm, reflection, util"
```

---

## Task 4: 创建 concurrent 模块

**Files:**
- Create: `modules/concurrent/build.gradle`
- Create: `modules/concurrent/src/main/java/com/wx/study/concurrent/`

- [ ] **Step 1: 创建目录结构**

```bash
mkdir -p modules/concurrent/src/main/java/com/wx/study
mkdir -p modules/concurrent/src/test/java
```

- [ ] **Step 2: 迁移 concurrent 代码**

```bash
cp -r src/main/java/com/wx/study/concurrent modules/concurrent/src/main/java/com/wx/study/
```

- [ ] **Step 3: 创建 build.gradle**

```groovy
dependencies {
    implementation project(':modules:core')
}
```

- [ ] **Step 4: 验证 concurrent 模块编译**

```bash
gradle :modules:concurrent:compileJava
```

Expected: BUILD SUCCESSFUL

- [ ] **Step 5: 提交**

```bash
git add modules/concurrent/
git commit -m "feat: create concurrent module"
```

---

## Task 5: 创建 patterns 模块

**Files:**
- Create: `modules/patterns/build.gradle`
- Create: `modules/patterns/src/main/java/com/wx/study/patterns/`

- [ ] **Step 1: 创建目录结构**

```bash
mkdir -p modules/patterns/src/main/java/com/wx/study
mkdir -p modules/patterns/src/test/java
```

- [ ] **Step 2: 迁移 patterns 代码**

```bash
cp -r src/main/java/com/wx/study/patterns modules/patterns/src/main/java/com/wx/study/
```

- [ ] **Step 3: 创建 build.gradle**

```groovy
dependencies {
    implementation project(':modules:core')
}
```

- [ ] **Step 4: 验证 patterns 模块编译**

```bash
gradle :modules:patterns:compileJava
```

Expected: BUILD SUCCESSFUL

- [ ] **Step 5: 提交**

```bash
git add modules/patterns/
git commit -m "feat: create patterns module"
```

---

## Task 6: 创建 io 模块

**Files:**
- Create: `modules/io/build.gradle`
- Create: `modules/io/src/main/java/com/wx/study/io/`

- [ ] **Step 1: 创建目录结构**

```bash
mkdir -p modules/io/src/main/java/com/wx/study
mkdir -p modules/io/src/test/java
```

- [ ] **Step 2: 迁移 io 代码**

```bash
cp -r src/main/java/com/wx/study/io modules/io/src/main/java/com/wx/study/
```

- [ ] **Step 3: 创建 build.gradle**

```groovy
dependencies {
    implementation project(':modules:core')
}
```

- [ ] **Step 4: 验证 io 模块编译**

```bash
gradle :modules:io:compileJava
```

Expected: BUILD SUCCESSFUL

- [ ] **Step 5: 提交**

```bash
git add modules/io/
git commit -m "feat: create io module"
```

---

## Task 7: 创建 algorithm 模块

**Files:**
- Create: `modules/algorithm/build.gradle`
- Create: `modules/algorithm/src/main/java/com/wx/study/algorithm/`

- [ ] **Step 1: 创建目录结构**

```bash
mkdir -p modules/algorithm/src/main/java/com/wx/study
mkdir -p modules/algorithm/src/test/java
```

- [ ] **Step 2: 迁移 algorithm 代码**

```bash
cp -r src/main/java/com/wx/study/algorithm modules/algorithm/src/main/java/com/wx/study/
```

- [ ] **Step 3: 创建 build.gradle**

```groovy
dependencies {
    implementation project(':modules:core')
}
```

- [ ] **Step 4: 验证 algorithm 模块编译**

```bash
gradle :modules:algorithm:compileJava
```

Expected: BUILD SUCCESSFUL

- [ ] **Step 5: 提交**

```bash
git add modules/algorithm/
git commit -m "feat: create algorithm module"
```

---

## Task 8: 创建 framework 模块

**Files:**
- Create: `modules/framework/build.gradle`
- Create: `modules/framework/src/main/java/com/wx/study/framework/`

- [ ] **Step 1: 创建目录结构**

```bash
mkdir -p modules/framework/src/main/java/com/wx/study
mkdir -p modules/framework/src/test/java
```

- [ ] **Step 2: 迁移 framework 代码**

```bash
cp -r src/main/java/com/wx/study/framework modules/framework/src/main/java/com/wx/study/
```

- [ ] **Step 3: 创建 build.gradle**

```groovy
dependencies {
    implementation project(':modules:core')
}
```

- [ ] **Step 4: 验证 framework 模块编译**

```bash
gradle :modules:framework:compileJava
```

Expected: BUILD SUCCESSFUL

- [ ] **Step 5: 提交**

```bash
git add modules/framework/
git commit -m "feat: create framework module"
```

---

## Task 9: 创建 demo 模块

**Files:**
- Create: `modules/demo/build.gradle`
- Create: `modules/demo/src/main/java/com/wx/study/demo/`

- [ ] **Step 1: 创建目录结构**

```bash
mkdir -p modules/demo/src/main/java/com/wx/study
mkdir -p modules/demo/src/test/java
```

- [ ] **Step 2: 迁移 demo 代码**

```bash
cp -r src/main/java/com/wx/study/demo modules/demo/src/main/java/com/wx/study/
```

- [ ] **Step 3: 创建 build.gradle**

```groovy
dependencies {
    implementation project(':modules:core')
}
```

- [ ] **Step 4: 验证 demo 模块编译**

```bash
gradle :modules:demo:compileJava
```

Expected: BUILD SUCCESSFUL

- [ ] **Step 5: 提交**

```bash
git add modules/demo/
git commit -m "feat: create demo module"
```

---

## Task 10: 清理旧 src 目录

**Files:**
- Delete: `src/main/java/com/wx/study/jvm/`
- Delete: `src/main/java/com/wx/study/reflection/`
- Delete: `src/main/java/com/wx/study/util/`
- Delete: `src/main/java/com/wx/study/concurrent/`
- Delete: `src/main/java/com/wx/study/patterns/`
- Delete: `src/main/java/com/wx/study/io/`
- Delete: `src/main/java/com/wx/study/algorithm/`
- Delete: `src/main/java/com/wx/study/framework/`
- Delete: `src/main/java/com/wx/study/demo/`

- [ ] **Step 1: 验证所有模块编译成功**

```bash
gradle clean build
```

Expected: BUILD SUCCESSFUL（algorithm 模块可能有编译问题，因为是遗留代码）

- [ ] **Step 2: 删除已迁移的目录**

```bash
rm -rf src/main/java/com/wx/study/jvm
rm -rf src/main/java/com/wx/study/reflection
rm -rf src/main/java/com/wx/study/util
rm -rf src/main/java/com/wx/study/concurrent
rm -rf src/main/java/com/wx/study/patterns
rm -rf src/main/java/com/wx/study/io
rm -rf src/main/java/com/wx/study/algorithm
rm -rf src/main/java/com/wx/study/framework
rm -rf src/main/java/com/wx/study/demo
```

- [ ] **Step 3: 验证清理后编译**

```bash
gradle :modules:core:build :modules:concurrent:build :modules:patterns:build :modules:io:build :modules:algorithm:build :modules:framework:build :modules:demo:build :realtime:build
```

Expected: BUILD SUCCESSFUL

- [ ] **Step 4: 提交**

```bash
git add src/
git commit -m "refactor: remove old src directories after module migration"
```

---

## Task 11: 最终验证

**Files:**
- N/A

- [ ] **Step 1: 验证模块结构**

```bash
find modules -type d -maxdepth 2 | sort
```

Expected: 显示 7 个模块目录

- [ ] **Step 2: 验证所有模块编译**

```bash
gradle build 2>&1 | tail -20
```

Expected: 大部分模块 BUILD SUCCESSFUL

- [ ] **Step 3: 查看 Git 状态**

```bash
git status
```

Expected: 工作区干净

- [ ] **Step 4: 查看提交历史**

```bash
git log --oneline -10
```

Expected: 显示所有模块创建提交

---

## 验收标准

- [ ] 7 个新模块创建成功
- [ ] core 模块包含 jvm、reflection、util，包名已更新为 `com.wx.study.core.*`
- [ ] 其他模块代码迁移完成，包名保持不变
- [ ] 所有模块编译成功（除已有算法代码问题外）
- [ ] 旧 src 目录已清理
- [ ] Git 提交历史清晰

---

Plan complete and saved to `docs/superpowers/plans/2026-04-16-java-module-split-plan.md`. Two execution options:

**1. Subagent-Driven (recommended)** - I dispatch a fresh subagent per task, review between tasks, fast iteration

**2. Inline Execution** - Execute tasks in this session using executing-plans, batch execution with checkpoints for review

**Which approach?**
