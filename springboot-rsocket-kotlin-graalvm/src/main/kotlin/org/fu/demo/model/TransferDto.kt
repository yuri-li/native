package org.fu.demo.model

import kotlinx.serialization.Serializable

@Serializable
data class TransferDto(
    val from: String = "德刚",
    val to: String = "Andy",
    val amount: Int,
)