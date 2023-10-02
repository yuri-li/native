<script setup lang="ts">
import { requestResponse } from "@/config/api/rsocket"

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

const user = await requestResponse<User>("anonymous.user.profile")
</script>
<template>
  <div class="card">
    <h1>request-response</h1>
    <h2>{{ user }}</h2>
    <p>
      Edit <code>components/UserProfile.vue</code> to test HMR
    </p>
  </div>
</template>