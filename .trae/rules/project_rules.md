# 项目规则

## UI 框架
- **必须使用 miuix**（top.yukonga.miuix.kmp:miuix-ui）作为 UI 组件库
- 禁止使用 Material UI、Material3 或其他 UI 框架
- 项目基于 Compose Multiplatform（Kotlin）

## 技术栈
- 语言：Kotlin
- UI 框架：Compose Multiplatform + miuix
- 构建系统：Gradle（Kotlin DSL）
- 目标平台：Web（WasmJs）

## 数据模型
- 科类类型使用 `SubjectType.PHYSICS`（物理类）和 `SubjectType.HISTORY`（历史类）
- 禁止使用旧的 "SCIENCE/LIBERAL_ARTS" 或 "理科/文科" 分类
- 分数字段使用 `physicsLow/physicsAvg/physicsHigh` 和 `historyLow/historyAvg/historyHistoryHigh`

## miuix API 注意事项
- NavigationBarItem 的 icon 参数是 ImageVector 类型，不是 Composable
- Card 的 onClick 参数是可空的 (() -> Unit)?
- Scaffold 的 content 接收 PaddingValues 参数
- TextField 使用 value: String + onValueChange: (String) -> Unit 重载
- TextField 没有 placeholder 参数，使用 label + useLabelAsPlaceholder = true
- miuix 没有 Chip 组件，使用 TextButton 替代
- Card 的 insideMargin 类型是 PaddingValues
- TextField 的 insideMargin 类型是 DpSize

## 构建和部署
- 使用 GitHub Actions 自动构建、部署到 GitHub Pages 和创建 Release
- 构建命令：`./gradlew :web:wasmJsBrowserDistribution`
- 构建产物路径：`web/build/dist/wasmJs/productionExecutable`
