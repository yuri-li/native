<script setup lang="ts">
import {ref} from "vue"
import {createClient,} from "@/config/api/rsocket.ts"

defineProps<{ msg: string }>()

const count = ref(0)
const connector = createClient({
  port: 7001,
  mappingPath: "/demo",
})
</script>

<template>
  <h1>{{ msg }}</h1>
  <h2>{{ connector }}</h2>

  <div class="card">
    <button type="button" @click="count++">count is {{ count }}</button>
    <p>
      Edit
      <code>components/HelloWorld.vue</code> to test HMR
    </p>
  </div>

  <p>
    Check out
    <a href="https://vuejs.org/guide/quick-start.html#local" target="_blank"
    >create-vue</a
    >, the official Vue + Vite starter
  </p>
  <p>
    Install
    <a href="https://github.com/vuejs/language-tools" target="_blank">Volar</a>
    in your IDE for a better DX
  </p>
  <p class="read-the-docs">Click on the Vite and Vue logos to learn more</p>
</template>

<style scoped>
.read-the-docs {
  color: #888;
}
</style>
