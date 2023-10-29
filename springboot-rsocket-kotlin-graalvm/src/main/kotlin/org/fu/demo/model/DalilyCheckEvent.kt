package org.fu.demo.model

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import org.komapper.annotation.KomapperCreatedAt
import org.komapper.annotation.KomapperEntityDef
import org.komapper.annotation.KomapperId
import org.komapper.annotation.KomapperTable

@Serializable
data class DailyCheckEvent(
    val id: String,
    val username: String,
    val createdAt: LocalDateTime? = null,
)

@KomapperEntityDef(DailyCheckEvent::class, aliases = ["dailyCheckEvent"])
@KomapperTable("t_daily_check_event")
data class DalilyCheckEventDef(
    @KomapperId
    val id: Nothing,
    @KomapperCreatedAt
    val createdAt: Nothing,
)