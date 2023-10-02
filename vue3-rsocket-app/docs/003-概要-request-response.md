# 1 场景描述

**1. 后端接口**

后端RSocket开放的接口（学习的第一个接口，尽可能简单些），要求如下：

- 没有参数

- 返回值是json对象

- delay 5秒，模拟服务执行的时间

**2. 前端**

- 与后端建立链接

- 发送`request-response`请求

- 接收响应，且，转换为typescript class

- 将model展示在页面上

# 2 后端接口概览

## 2.1 接口

```kotlin
@MessageMapping("anonymous.user.profile")
suspend fun profile(): User {
    val model = User(
        id = UUID.randomUUID().toString(),
        age = 18,
        gender = Gender.Male,
        time = LocalDateTime.now().toKotlinLocalDateTime()
    )
    log.info("查看用户资料 {}", model)
    delay(5000)
    return model
}
```

## 2.2 model

```kotlin
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String,
    val age: Int,
    val gender: Gender,
    val time: LocalDateTime
)

@Serializable
enum class Gender {
    Male, Female
}
```

## 2.3 启动服务

```bash
# 1 进入项目根目录
# 2 执行gradle clean，清理环境
# 3 执行gradle nativeCompile，构建可执行文件
# 4 双击执行，或，进入terminal，输入下面的命令，直接回车执行
/Users/yuri/workspace/idea/study/native/springboot-rsocket-kotlin-graalvm/build/native/nativeCompile/springboot-rsocket-kotlin-graalvm
```

# 3 前端

## 3.1 首页

将`HelloWorld.vue`作为首页，两个目的：

- 懒得写css，使用已有的样式，稍微好看些。否则，光秃秃的页面太难看了

- 访问后端接口至少需要5秒，观察前端页面的加载效果

```typescript
<script setup lang="ts">
import UserProfile from "@/components/UserProfile.vue"
...
</script>
<template>
...
<Suspense>
    <AddUser />
  </Suspense>
...
</template>
```

## 3.2 UserProfile.vue

```typescript
<script setup lang="ts">
import { requestResponse } from "@/config/api/operators/anonymous/requestResponse"

const user = await requestResponse<User>({route: "anonymous.user.profile"})
</script>
<template>
  <h2>{{ user }}</h2>
</template>
```

## 3.3 model

```typescript
class User {
  id: string | undefined
  age: number | undefined
  gender: Gender | undefined
  time: Date | undefined
}

enum Gender {
  Male = "Male",
  Female = "Female"
}
```

# 
