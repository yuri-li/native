<template>
  <div style="width:500px;padding-left:200px;display: flex; flex-wrap: wrap; align-content: space-between; background-color: aliceblue;">
    <span class="nav-link"><router-link to="/counter">counter</router-link></span>
    <span class="nav-link"><router-link to="/user/profile">user profile</router-link></span>
    <span class="nav-link"><router-link to="/user/ids">user ids</router-link></span>
    <span class="nav-link"><router-link to="/cash/transfer">转账</router-link></span>
  </div>
  <router-view v-slot="{ Component }">
    <Suspense>
      <template #default>
        <component :is="Component"/>
      </template>
      <template #fallback>
        <div>Loading...</div>
      </template>
    </Suspense>
  </router-view>
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
.nav-link{
  padding-right: 30px;
}
</style>
<script setup lang="ts">
</script>