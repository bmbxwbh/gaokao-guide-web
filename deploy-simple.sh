#!/bin/bash

# 蓉城高考指南 - 简化版部署脚本
# 使用 Nginx 直接托管静态文件
# 使用方法: ./deploy-simple.sh

set -e

GREEN='\033[0;32m'
BLUE='\033[0;34m'
NC='\033[0m'

# 配置
PROJECT_DIR="/var/www/gaokao-guide"
PORT="80"

echo -e "${GREEN}"
echo "=================================="
echo "   蓉城高考指南 - 快速部署"
echo "=================================="
echo -e "${NC}"

# 检查 root
if [ "$EUID" -ne 0 ]; then 
    echo "请使用 root 运行: sudo ./deploy-simple.sh"
    exit 1
fi

# 更新并安装依赖
echo -e "${BLUE}[1/6]${NC} 更新系统..."
apt update && apt install -y nginx git curl

# 安装 Node.js
echo -e "${BLUE}[2/6]${NC} 安装 Node.js..."
curl -fsSL https://deb.nodesource.com/setup_20.x | bash -
apt install -y nodejs

# 克隆项目
echo -e "${BLUE}[3/6]${NC} 拉取项目..."
rm -rf $PROJECT_DIR
git clone https://github.com/bmbxwbh/gaokao-guide-web.git $PROJECT_DIR
cd $PROJECT_DIR

# 构建项目
echo -e "${BLUE}[4/6]${NC} 构建项目..."
npm ci
npm run build

# 配置 Nginx
echo -e "${BLUE}[5/6]${NC} 配置 Nginx..."
cat > /etc/nginx/sites-available/gaokao-guide << 'EOF'
server {
    listen 80;
    server_name _;
    root /var/www/gaokao-guide/dist;
    index index.html;

    location / {
        try_files $uri $uri/ /index.html;
    }

    location ~* \.(js|css|png|jpg|jpeg|gif|ico|svg|woff|woff2|ttf|eot)$ {
        expires 1y;
        add_header Cache-Control "public, immutable";
    }
}
EOF

ln -sf /etc/nginx/sites-available/gaokao-guide /etc/nginx/sites-enabled/
rm -f /etc/nginx/sites-enabled/default

# 启动服务
echo -e "${BLUE}[6/6]${NC} 启动服务..."
nginx -t && systemctl reload nginx

echo ""
echo -e "${GREEN}=================================="
echo "部署成功！"
echo "访问地址: http://$(curl -s ifconfig.me)"
echo "项目目录: $PROJECT_DIR"
echo "==================================${NC}"
