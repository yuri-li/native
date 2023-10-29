package org.fu.demo.model

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import org.komapper.annotation.*

@Serializable
data class User(
    val id: String,
    val age: Int,
    val gender: Gender,
    val time: LocalDateTime,
    val version: Int = 0,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null,
)

@Serializable
enum class Gender {
    Male, Female
}

@KomapperEntityDef(User::class, aliases = ["user"])
@KomapperTable("t_user")
data class UserDef(
    @KomapperId
    val id: Nothing,
    @KomapperVersion
    val version: Nothing,
    @KomapperCreatedAt
    val createdAt: Nothing,
    @KomapperUpdatedAt
    val updatedAt: Nothing,
)