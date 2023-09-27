# 1 package name could not use "*.native.*"

## 1.1 场景描述

`package name` 如果包含 "native"关键字，则执行 "./gradlew :compileTestJava"会抛异常。

![](assets/2023-09-27-19-50-35-image.png)

## 1.2 解决方案

换个名字就好了

![](assets/2023-09-28-00-46-50-image.png)