package org.fu.demo.controller

import org.fu.demo.service.DailyCheckService
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.stereotype.Controller

@Controller
class DailyCheckController(val service: DailyCheckService) {
    @MessageMapping("anonymous.check.initDB")
    suspend fun initDB() = service.initDB()

    @MessageMapping("anonymous.check.clockIn")
    suspend fun clockIn(username: String) = service.clockIn(username)

    @MessageMapping("anonymous.check.count")
    suspend fun count(list: List<String>) = service.count(list)

}