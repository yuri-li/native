package org.fu.demo.controller

import jakarta.validation.Valid
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.datetime.toKotlinLocalDateTime
import org.fu.demo.model.Gender
import org.fu.demo.model.TransferDto
import org.fu.demo.model.User
import org.fu.demo.service.UserService
import org.slf4j.LoggerFactory
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.stereotype.Controller
import org.springframework.validation.annotation.Validated
import java.time.LocalDateTime
import java.util.*

@Validated
@Controller
class UserController(val service:UserService) {
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
    suspend fun transfer(@Valid dto: TransferDto) {
        log.info("${dto.from}给${dto.to}转账${dto.amount}元")
    }

    @MessageMapping("anonymous.user.initDB")
    suspend fun initDB() = service.initDB()
    @MessageMapping("anonymous.user.add")
    suspend fun add() = service.add()

    @MessageMapping("anonymous.user.findAll")
    suspend fun findAll(): Flow<User> = service.findAll()
}