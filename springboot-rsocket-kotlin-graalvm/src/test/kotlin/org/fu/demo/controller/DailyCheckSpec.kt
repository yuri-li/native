package org.fu.demo.controller

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.longs.shouldBeLessThan
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.slf4j.LoggerFactory
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.messaging.rsocket.RSocketRequester
import org.springframework.messaging.rsocket.retrieveMono
import kotlin.time.measureTime

@SpringBootTest
class DailyCheckSpec(val requester: RSocketRequester) : StringSpec({
    "为什么要🔒" {
        val time = measureTime {
            requester.route("anonymous.check.initDB")
                .send()
                .awaitSingleOrNull()
            val users = listOf("yuri", "alice", "kikk")
            users.map { username ->
                (1..100).map {
                    launch {
                        requester.route("anonymous.check.clockIn")
                            .data(username)
                            .send()
                            .awaitSingleOrNull()
                    }
                }
            }.flatten().joinAll()
            val msg = requester.route("anonymous.check.count")
                .data(Json.encodeToString(users))
                .retrieveMono<String>()
                .awaitSingle()
            log.info(msg)
        }
        log.info("耗时: $time")
        time.inWholeSeconds.shouldBeLessThan(1)
    }
}) {
    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }
}