<script setup lang="ts">
import { onUnmounted } from "vue"

const key = `create-${new Date()}`
function saveCache(){
  const date = JSON.stringify(new Date())
  console.log("save cache, key= `{}`, value = `{}`", key, date)
  localStorage.setItem(key, date)
}
onUnmounted(() => {
  console.log("remove cache Item, key= `{}`", key)
  localStorage.removeItem(key)
})
</script>
<template>
  <button @click="saveCache">加入缓存</button>

</template>