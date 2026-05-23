#!/bin/bash

# 蓉城高考指南 - 服务器一键部署脚本
# 使用方法: ./deploy.sh

set -e  # 遇到错误立即退出

# 颜色定义
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
RED='\033[0;31m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# 打印带颜色的消息
print_info() {
    echo -e "${BLUE}[INFO]${NC} $1"
}

print_success() {
    echo -e "${GREEN}[SUCCESS]${NC} $1"
}

print_warning() {
    echo -e "${YELLOW}[WARNING]${NC} $1"
}

print_error() {
    echo -e "${RED}[ERROR]${NC} $1"
}

# 项目配置
PROJECT_NAME="蓉城高考指南"
PROJECT_DIR="/root/gaokao-guide-web"
GIT_REPO="https://github.com/bmbxwbh/gaokao-guide-web.git"
NODE_VERSION="20"
PORT="3000"

echo -e "${GREEN}"
echo "======================================"
echo "   蓉城高考指南 - 服务器部署"
echo "======================================"
echo -e "${NC}"

# 1. 检查是否为 root 用户
if [ "$EUID" -ne 0 ]; then 
    print_error "请使用 root 运行此脚本"
    print_info "使用: sudo ./deploy.sh"
    exit 1
fi

# 2. 更新系统
print_info "更新系统..."
apt update && apt upgrade -y

# 3. 安装必要工具
print_info "安装基础工具..."
apt install -y curl git build-essential nginx

# 4. 安装 Node.js
print_info "安装 Node.js $NODE_VERSION..."
curl -fsSL https://deb.nodesource.com/setup_$NODE_VERSION.x | bash -
apt install -y nodejs

# 5. 验证 Node.js 安装
print_info "验证安装..."
node -v
npm -v

# 6. 全局安装 pm2
print_info "安装 PM2 进程管理器..."
npm install -g pm2

# 7. 克隆或拉取项目
if [ -d "$PROJECT_DIR" ]; then
    print_info "项目目录已存在，拉取最新代码..."
    cd $PROJECT_DIR
    git stash
    git pull origin main
else
    print_info "克隆项目仓库..."
    git clone $GIT_REPO $PROJECT_DIR
    cd $PROJECT_DIR
fi

# 8. 安装项目依赖
print_info "安装项目依赖..."
npm ci

# 9. 构建项目
print_info "构建项目..."
npm run build

# 10. 停止旧的 PM2 进程（如果存在）
print_info "停止旧进程..."
pm2 delete gaokao-guide 2>/dev/null || true

# 11. 使用 PM2 启动预览服务器
print_info "启动服务..."
pm2 start npm --name "gaokao-guide" -- run preview -- --host 0.0.0.0 --port $PORT

# 12. 配置 PM2 开机自启
print_info "配置开机自启..."
pm2 save
pm2 startup systemd -u root --hp /root || true

# 13. 配置 Nginx（可选）
print_info "配置 Nginx..."
NGINX_CONF="/etc/nginx/sites-available/gaokao-guide"

cat > $NGINX_CONF << 'EOF'
server {
    listen 80;
    server_name _;

    root /root/gaokao-guide-web/dist;
    index index.html;

    location / {
        try_files $uri $uri/ /index.html;
    }

    # 如果需要通过 Node.js 服务器处理，取消下面的注释：
    # location / {
    #     proxy_pass http://localhost:3000;
    #     proxy_http_version 1.1;
    #     proxy_set_header Upgrade $http_upgrade;
    #     proxy_set_header Connection 'upgrade';
    #     proxy_set_header Host $host;
    #     proxy_cache_bypass $http_upgrade;
    # }

    # 静态资源缓存
    location ~* \.(js|css|png|jpg|jpeg|gif|ico|svg|woff|woff2|ttf|eot)$ {
        expires 1y;
        add_header Cache-Control "public, immutable";
    }
}
EOF

# 启用 Nginx 配置
ln -sf $NGINX_CONF /etc/nginx/sites-enabled/

# 删除默认配置
rm -f /etc/nginx/sites-enabled/default

# 测试并重启 Nginx
print_info "重启 Nginx..."
nginx -t && nginx -s reload

# 14. 显示部署信息
print_success "部署完成！"
echo ""
echo -e "${GREEN}======================================"
echo "项目名称: $PROJECT_NAME"
echo "项目目录: $PROJECT_DIR"
echo "访问地址: http://$(curl -s ifconfig.me):$PORT"
echo "PM2 状态: pm2 status"
echo "查看日志: pm2 logs gaokao-guide"
echo "======================================${NC}"

print_warning "温馨提示: 如果服务器有防火墙，请开放端口 $PORT"
print_info "防火墙命令: ufw allow $PORT"
