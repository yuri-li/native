package org.fu.demo.model

import jakarta.validation.constraints.Max
import kotlinx.serialization.Serializable

@Serializable
data class TransferDto(
    val from: String = "德刚",
    val to: String = "Andy",
    @field:Max(20, message = "转账金额 \${validatedValue} > {value}，账户余额不足")
    val amount: Int,
)