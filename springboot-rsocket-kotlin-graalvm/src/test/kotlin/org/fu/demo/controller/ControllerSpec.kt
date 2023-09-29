package org.fu.demo.controller

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.fu.demo.model.User
import org.slf4j.LoggerFactory
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.messaging.rsocket.RSocketRequester
import org.springframework.messaging.rsocket.retrieveFlow
import org.springframework.messaging.rsocket.retrieveMono


@SpringBootTest
class ControllerSpec(val requester: RSocketRequester) : StringSpec({
    "hello" {
        requester
            .route("anonymous.greet")
            .retrieveMono<User>()
            .awaitFirst().age.shouldBe(18)
    }
    "user list" {
        requester.route("user.list")
            .retrieveFlow<User>()
            .onEach { log.info("loaded from server: {}", Json.encodeToString(it)) }
            .toList().size.shouldBe(3)
    }
}) {
    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }
}