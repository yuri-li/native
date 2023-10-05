package org.fu.demo.controller

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.datetime.toKotlinLocalDateTime
import org.fu.demo.config.BusinessException
import org.fu.demo.model.Gender
import org.fu.demo.model.TransferDto
import org.fu.demo.model.User
import org.slf4j.LoggerFactory
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.stereotype.Controller
import java.time.LocalDateTime
import java.util.*

@Controller
class UserController {
    private val log = LoggerFactory.getLogger(this::class.java)

    @MessageMapping("anonymous.user.profile")
    suspend fun profile(): User {
        val model = User(
            id = UUID.randomUUID().toString(),
            age = 18,
            gender = Gender.Male,
            time = LocalDateTime.now().toKotlinLocalDateTime()
        )
        log.info("查看用户资料 {}", model)
        delay(5000)
        return model
    }

    @MessageMapping("anonymous.user.ids")
    suspend fun ids(): Flow<Int> = flow {
        repeat(5) {
            delay(1000)
            emit(it)
        }
    }

    @MessageMapping("anonymous.cash.transfer")
    suspend fun transfer(dto: TransferDto) {
        if (dto.amount > 20) {
            throw BusinessException("账户余额不足")
        }
        log.info("${dto.from}给${dto.to}转账${dto.amount}元")
    }
}