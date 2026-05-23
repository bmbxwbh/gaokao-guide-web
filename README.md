# 蓉城高考指南

一个基于 React + TypeScript + Vite 构建的成都市高校录取分数线查询网站，采用 Miuix 风格设计。

[![CI - Build and Test](https://github.com/your-username/gaokao-guide-web/actions/workflows/ci.yml/badge.svg)](https://github.com/your-username/gaokao-guide-web/actions/workflows/ci.yml)
[![CD - Deploy to GitHub Pages](https://github.com/your-username/gaokao-guide-web/actions/workflows/deploy.yml/badge.svg)](https://github.com/your-username/gaokao-guide-web/actions/workflows/deploy.yml)

## 功能特性

- 🏫 查看成都市各大高校信息
- 🔍 支持学校搜索和类型筛选
- 📊 查看学校和专业历年录取分数线
- 📱 响应式设计，支持移动端访问
- 🎨 Miuix 风格 UI 设计

## 技术栈

- **前端框架**: React 18
- **类型系统**: TypeScript
- **构建工具**: Vite
- **路由管理**: React Router v6
- **样式方案**: 原生 CSS + CSS 变量

## 项目结构

```
gaokao-guide-web/
├── public/
│   └── images/              # 高校图片资源
├── src/
│   ├── components/          # 可复用组件
│   │   ├── Card.tsx
│   │   ├── Button.tsx
│   │   ├── Chip.tsx
│   │   └── Input.tsx
│   ├── pages/               # 页面组件
│   │   ├── Home.tsx
│   │   ├── UniversityDetail.tsx
│   │   └── MajorDetail.tsx
│   ├── data/                # 数据源
│   │   └── universities.ts
│   ├── types/               # TypeScript 类型定义
│   │   └── index.ts
│   ├── styles/              # 样式文件
│   │   ├── index.css
│   │   ├── components.css
│   │   └── pages.css
│   ├── App.tsx              # 主应用组件
│   └── main.tsx             # 应用入口
├── index.html
├── package.json
├── tsconfig.json
├── vite.config.ts
└── README.md
```

## 快速开始

### 安装依赖

```bash
npm install
```

### 启动开发服务器

```bash
npm run dev
```

访问 http://localhost:3000 查看应用。

### 构建生产版本

```bash
npm run build
```

构建产物将输出到 `dist` 目录。

### 预览生产构建

```bash
npm run preview
```

## 包含的高校

- 四川师范大学
- 成都中医药大学
- 成都理工大学
- 成都信息工程大学
- 成都大学
- 西华大学
- 西南民族大学
- 西南石油大学

## 设计风格

本项目采用 Miuix 设计风格，具有以下特点：

- 清爽的配色方案（蓝色系为主）
- 圆角卡片设计
- 柔和的阴影效果
- 良好的视觉层次
- 支持浅色/深色模式

## GitHub Actions CI/CD

本项目配置了完整的 CI/CD 工作流：

### CI 工作流 (`.github/workflows/ci.yml`)

**触发条件：
- 推送到 main/master 分支
- 创建 Pull Request
- 手动触发

**功能：**
- 类型检查（TypeScript）
- 构建验证
- 构建产物上传（保留7天）

### CD 工作流 (`.github/workflows/deploy.yml`)

**触发条件：**
- 推送到 main/master 分支
- 手动触发

**功能：**
- 自动构建项目
- 部署到 GitHub Pages

### 部署到 GitHub Pages

#### 前置准备

1. 在 GitHub 仓库设置中，进入 `Settings > Pages`
2. 在 `Build and deployment` 部分：
   - Source 选择 `GitHub Actions`
3. 保存设置

#### 部署后的访问地址

部署成功后，可通过以下地址访问：
```
https://your-username.github.io/repo-name/
```

**注意**：项目已在 `vite.config.ts` 中配置了自动检测 GitHub Pages 环境，会自动设置正确的 base URL。

## 许可证

MIT
