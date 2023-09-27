package org.fu.demo.controller

import kotlinx.coroutines.delay
import kotlinx.datetime.toKotlinLocalDateTime
import org.fu.demo.model.Gender
import org.fu.demo.model.User
import org.slf4j.LoggerFactory
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.stereotype.Controller
import java.time.LocalDateTime
import java.util.*

@Controller
class HelloController {
    private val log = LoggerFactory.getLogger(this::class.java)

    @MessageMapping("anonymous.greet")
    suspend fun greet(): User {
        val model = User(
            id = UUID.randomUUID().toString(),
            age = 18,
            gender = Gender.Male,
            time = LocalDateTime.now().toKotlinLocalDateTime()
        )
        log.info("创建新用户 {}", model)
        delay(5000)
        return model
    }
}