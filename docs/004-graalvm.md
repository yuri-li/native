# 1 运行native程序

将springboot项目编译为本地可执行文件

![](assets/2023-09-28-02-13-01-image.png)

![](assets/2023-09-28-02-18-36-image.png)

![](assets/2023-09-28-02-20-06-image.png)

![](assets/2023-09-28-02-33-04-image.png)

![](assets/2023-09-28-02-37-45-image.png)

# 2 测试

```
# 1 打开新的terminal，安装rsc。https://github.com/making/rsc
brew install making/tap/rsc

# 2 发送rsocket请求
rsc --request --route=anonymous.greet ws://localhost:7001/demo
```

![](assets/2023-09-28-03-04-05-image.png)
