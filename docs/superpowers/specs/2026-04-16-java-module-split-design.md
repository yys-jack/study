# Java 模块拆分方案设计

**日期**: 2026-04-16  
**状态**: 已审核

---

## 1. 背景与目标

### 1.1 现状

当前项目代码集中在 `src/main/java/com/wx/study/` 目录下，包含 9 个领域目录：

| 领域 | Java 文件数 | 说明 |
|------|------------|------|
| algorithm | ~50 | 算法与数据结构 |
| concurrent | ~60 | 并发编程 |
| demo | ~5 | 演示代码 |
| framework | ~10 | 框架代码 |
| io | ~10 | IO/NIO |
| jvm | ~15 | JVM 相关 |
| patterns | ~15 | 设计模式 |
| reflection | ~15 | 反射与代理 |
| util | ~10 | 工具类 |

**问题**:
- 代码集中在单模块中，编译时间长
- 领域边界不清晰
- 无法按需依赖特定功能

### 1.2 目标

1. **模块化拆分** - 将代码按领域拆分为独立的 Gradle 子模块
2. **明确依赖关系** - 模块间依赖清晰可控
3. **保持编译通过** - 迁移后所有代码编译正常

---

## 2. 模块划分方案

### 2.1 模块总览

共 **7 个新模块** + 已有的 `realtime` 模块 = **8 个子模块**

| 模块名 | 路径 | 包含内容 | 依赖 |
|--------|------|---------|------|
| `core` | `modules/core/` | jvm + reflection + util | 无 |
| `concurrent` | `modules/concurrent/` | concurrent | core |
| `patterns` | `modules/patterns/` | patterns | core |
| `io` | `modules/io/` | io | core |
| `algorithm` | `modules/algorithm/` | algorithm | core |
| `framework` | `modules/framework/` | framework | core |
| `demo` | `modules/demo/` | demo | core |
| `realtime` | `realtime/` | realtime | 无 |

### 2.2 依赖关系图

```
┌─────────────────────────────────────────────────────────┐
│                      rootProject                        │
│  ┌─────────────────────────────────────────────────┐    │
│  │ core (jvm + reflection + util)                  │    │
│  └─────────────────────────────────────────────────┘    │
│                         │                                 │
│         ┌───────────────┼───────────────┐                │
│         │               │               │                │
│         ▼               ▼               ▼                │
│  ┌──────────┐   ┌──────────┐   ┌──────────┐            │
│  │algorithm │   │concurrent│   │patterns  │            │
│  └──────────┘   └──────────┘   └──────────┘            │
│                                                       │
│  ┌──────────┐   ┌──────────┐   ┌──────────┐            │
│  │io        │   │framework │   │demo      │            │
│  └──────────┘   └──────────┘   └──────────┘            │
│                                                       │
│  ┌──────────┐                                         │
│  │realtime  │ (独立)                                   │
│  └──────────┘                                         │
└─────────────────────────────────────────────────────────┘
```

---

## 3. 目录结构

### 3.1 整体布局

```
study/
├── modules/
│   ├── core/
│   │   ├── build.gradle
│   │   └── src/main/java/com/wx/study/core/
│   │       ├── jvm/
│   │       ├── reflection/
│   │       └── util/
│   ├── concurrent/
│   │   ├── build.gradle
│   │   └── src/main/java/com/wx/study/concurrent/
│   ├── patterns/
│   │   ├── build.gradle
│   │   └── src/main/java/com/wx/study/patterns/
│   ├── io/
│   │   ├── build.gradle
│   │   └── src/main/java/com/wx/study/io/
│   ├── algorithm/
│   │   ├── build.gradle
│   │   └── src/main/java/com/wx/study/algorithm/
│   ├── framework/
│   │   ├── build.gradle
│   │   └── src/main/java/com/wx/study/framework/
│   └── demo/
│       ├── build.gradle
│       └── src/main/java/com/wx/study/demo/
├── realtime/
│   ├── build.gradle
│   └── src/main/java/com/wx/study/realtime/
├── build.gradle          # 根构建配置（共享依赖）
└── settings.gradle       # 模块注册
```

### 3.2 包名映射

| 原包名 | 新包名 |
|--------|--------|
| `com.wx.study.jvm.*` | `com.wx.study.core.jvm.*` |
| `com.wx.study.reflection.*` | `com.wx.study.core.reflection.*` |
| `com.wx.study.util.*` | `com.wx.study.core.util.*` |
| `com.wx.study.concurrent.*` | `com.wx.study.concurrent.*` (不变) |
| `com.wx.study.patterns.*` | `com.wx.study.patterns.*` (不变) |
| `com.wx.study.io.*` | `com.wx.study.io.*` (不变) |
| `com.wx.study.algorithm.*` | `com.wx.study.algorithm.*` (不变) |
| `com.wx.study.framework.*` | `com.wx.study.framework.*` (不变) |
| `com.wx.study.demo.*` | `com.wx.study.demo.*` (不变) |

---

## 4. 构建配置

### 4.1 根 build.gradle

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
    }
}
```

### 4.2 modules/core/build.gradle

```groovy
dependencies {
    implementation 'org.openjdk.jol:jol-core:0.17'
    implementation 'cglib:cglib:3.3.0'
}
```

### 4.3 其他模块 build.gradle

```groovy
dependencies {
    implementation project(':modules:core')
}
```

### 4.4 settings.gradle

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

---

## 5. 实施步骤

### 阶段一：准备
1. 创建 `modules/` 目录
2. 更新根 `build.gradle` 支持子项目
3. 更新 `settings.gradle` 注册新模块

### 阶段二：创建 core 模块
1. 迁移 jvm、reflection、util 到 core 模块
2. 更新包名 `com.wx.study.*` → `com.wx.study.core.*`
3. 验证编译

### 阶段三：创建其他模块
1. 按顺序创建 concurrent、patterns、io、algorithm、framework、demo
2. 迁移代码并更新包名
3. 配置模块依赖

### 阶段四：清理
1. 删除旧 `src/` 目录
2. 全量编译验证
3. 提交

---

## 6. 风险与缓解

| 风险 | 缓解措施 |
|------|---------|
| 包名更新遗漏 | 使用 IDE 全局替换 + 编译验证 |
| 循环依赖 | core 模块不依赖其他模块 |
| 测试失败 | 迁移前后对比测试数量 |

---

## 7. 验收标准

- [ ] 8 个子模块编译成功
- [ ] 包名更新一致
- [ ] 旧 src 目录已删除
- [ ] Git 历史保留

---

## 8. 排除内容

本次拆分**不处理**：
- `docs/` - 文档保持原样
- `platform/` - 平台配置保持原样
