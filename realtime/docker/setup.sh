#!/bin/bash
# 创建 docker 配置目录

set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

# 创建目录
mkdir -p "$SCRIPT_DIR/mysql"
mkdir -p "$SCRIPT_DIR/canal/example"
mkdir -p "$SCRIPT_DIR/flink"

echo "Directory structure created:"
tree "$SCRIPT_DIR" 2>/dev/null || find "$SCRIPT_DIR" -type d | head -20
