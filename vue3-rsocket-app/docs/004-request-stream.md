# UserIds.vue

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
