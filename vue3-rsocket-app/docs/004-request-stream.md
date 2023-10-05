# 1 场景描述

学习“背压”的实现方式。

# 2 什么是流？

**1. 什么是分页？**

![7282.jpg_wh860.jpg](/Users/yuri/workspace/idea/study/native/vue3-rsocket-app/docs/assets/7282.jpg_wh860.jpg)

就像送水服务。一桶一桶的，我们需要时刻知道有几桶，每桶多少水。

**2. 什么是流？**

家里安装一套净水、热水设备，随时拧开就有水。谁知道自来水厂的池子里有多少水？谁想知道？为什么要知道？

**3. 什么是背压？**

“背压”，为了解决网络“阻塞”，提出的解决方案。

家里的水龙头就是“背压”的一种表现形式，拧开，水就流。

- 控制权在客户端手里，不是服务端

- 不管流速快还是慢，服务端都不用写一行代码

看起来很神奇，只是没见过而已。

# 3 UserIds.vue

```typescript
<script setup lang="ts">
import { reactive, ref, } from "vue"
import { Payload, requestStream, StreamResponse, } from "@/config/api/rsocket"

const requestN = 2
const disabledBtn = ref(false)
const tableData: number[] = reactive([])
let _requester: StreamResponse | null | undefined

function loadMore() {
  _requester?.request(requestN)
}

async function search() {
  tableData.length = 0
  disabledBtn.value = false
  _requester = await requestStream( "anonymous.user.ids", null, requestN, {
    onNext(payload: Payload, isComplete: boolean) {
      console.log(`payload[data: ${payload.data}; metadata: ${payload.metadata}] | ${isComplete}`)
      tableData.push(JSON.parse((payload.data as Buffer).toString()) as number)
    },
    onComplete() {
      disabledBtn.value = true
      alert("已加载完成")
    }
  })
}
</script>
<template>
  <div class="card">
    <h1>request-stream</h1>
    <button class="btn success" @click="search">查询</button>
    <hr/>
    <ul>
      <li v-for="item in tableData">
        {{ item }}
      </li>
    </ul>
    <button :class="{'btn': true, 'success': _requester && !disabledBtn}" :disabled="disabledBtn" @click="loadMore">
      加载更多...
    </button>
    <p>
      Edit <code>components/UserIds.vue</code> to test HMR
    </p>
  </div>

</template>
<style scoped>
.btn {
  border: 2px solid black;
  background-color: white;
  color: black;
  padding: 14px 28px;
  font-size: 16px;
  cursor: pointer;
}

/* Green */
.success {
  border-color: #04AA6D;
  color: green;
}

.success:hover {
  background-color: #04AA6D;
  color: white;
}
</style>
```
