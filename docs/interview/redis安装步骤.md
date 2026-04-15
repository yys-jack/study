#### contens os
```bash
yum install wget

wget ***redis.tar.gz

tar redis

cd **

# 可详看 readme.md 

# 生成makefile文件（可选）

# 编译源文件
make    

# 清除编译错误的临时文件
make   distclean

# 以软件形式安装到系统
make [prefix=/path] install

# 添加redis环境变量
vi /etc/profile

export REDIS_HOME=/root/redis-7.0.0/src                            
export PATH=$PATH:$REDIS_HOME/bin
source /etc/profile

cd utils

./install_server.sh

ps -ef | grep redis 

netstat -ntpl

service redis_port status/start/stop

```

//todo
systemd
init

#### 参考

[redis README.md](https://github.com/redis/redis/blob/7.0/README.md)
[redis官网](https://redis.io/)
[redis中文网](http://www.redis.cn/)
[systemd](https://www.ruanyifeng.com/blog/2016/03/systemd-tutorial-commands.html)
[init进程](https://en.wikipedia.org/wiki/Init)