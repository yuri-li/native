<script setup lang="ts">
import { reactive, ref, } from "vue"
import { Payload, requestStream, StreamResponse, fireAndForget, } from "@/config/api/rsocket"
import { User } from "@/components/userProfile/model"


const requestN = 2
const [disableInitDB, disableSearch, disableAddUser, disableLoadMore] = [ref(false), ref(true), ref(true), ref(true)]
const tableData: User[] = reactive([])
let _requester: StreamResponse | null | undefined

function loadMore() {
  _requester?.request(requestN)
}

async function search() {
  tableData.length = 0
  disableLoadMore.value = false
  _requester = await requestStream( "anonymous.user.findAll", null, requestN, {
    onNext(payload: Payload, isComplete: boolean) {
      console.log(`payload[data: ${payload.data}; metadata: ${payload.metadata}] | ${isComplete}`)
      tableData.push(JSON.parse((payload.data as Buffer).toString()) as User)
    },
    onComplete() {
      disableLoadMore.value = true
      alert("已加载完成")
    }
  })
}
async function initDB(){
  await fireAndForget("anonymous.user.initDB")
  disableSearch.value = false
  disableAddUser.value = false
  disableInitDB.value = true
}
async function addUser(){
  await fireAndForget("anonymous.user.add")
  disableAddUser.value = true
}
</script>
<template>
  <div class="card">
    <h1>request-stream</h1>
    <button :class="{'btn': true, 'success': !disableInitDB}" :disabled="disableInitDB" @click="initDB">初始化数据库</button>
    <button :class="{'btn': true, 'success': !disableSearch}" :disabled="disableSearch" @click="search">查询</button>
    <button :class="{'btn': true, 'success': !disableAddUser}" :disabled="disableAddUser" @click="addUser">新增user("d")</button>

    <hr/>
    <ul>
      <li v-for="item in tableData">
        {{ item.id }}
      </li>
    </ul>
    <button v-if="!disableLoadMore" :class="{'btn': true, 'success': _requester && !disableLoadMore}" :disabled="disableLoadMore" @click="loadMore">
      加载更多...
    </button>
    <p>
      Edit <code>components/crud/read/Index.vue</code> to test HMR
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