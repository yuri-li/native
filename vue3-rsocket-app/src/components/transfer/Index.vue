<script setup lang="ts">
import {
  // fireAndForget,
  requestResponse
} from "@/config/api/rsocket"
import { TransferDto } from "@/components/transfer/model"
import { ref } from "vue"

const amount = ref()
async function changeItem(event: Event) {
  amount.value = parseInt((event.target as HTMLInputElement).value)
  // await fireAndForget("anonymous.cash.transfer", new TransferDto(amount.value))
  await requestResponse("anonymous.cash.transfer", new TransferDto(amount.value))
}
</script>
<template>
  <div class="card">
    <h1>fire adn forget</h1>
    <div>已选中金额: {{ amount }}</div>
    <select @change="changeItem">
      <option disabled value="">--请选择--</option>
      <option>10</option>
      <option>15</option>
      <option>25</option>
    </select>
    <p>
      Edit <code>components/transfer/Index.vue</code> to test HMR
    </p>
  </div>
</template>