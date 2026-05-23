import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

// 获取 GitHub 仓库信息用于正确配置 base URL
// 如果需要部署到自定义域名，请修改这里
const repoName = process.env.GITHUB_REPOSITORY?.split('/')[1] || ''
const isGitHubPages = process.env.GITHUB_ACTIONS === 'true'

export default defineConfig({
  plugins: [react()],
  base: isGitHubPages ? `/${repoName}/` : '/',
  server: {
    port: 3000,
    open: true
  },
  build: {
    outDir: 'dist',
    sourcemap: true
  }
})
