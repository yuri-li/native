package org.fu.demo.model

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String,
    val age: Int,
    val gender: Gender,
    val time: LocalDateTime
)

@Serializable
enum class Gender {
    Male, Female
}