package org.fu.demo.controller

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.reactive.awaitFirst
import org.fu.demo.model.User
import org.slf4j.LoggerFactory
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.messaging.rsocket.RSocketRequester


@SpringBootTest
class ControllerSpec(val requester: RSocketRequester) : StringSpec({
    "hello" {
        log.info("hello world -- start")
        requester
            .route("anonymous.greet")
            .retrieveMono(User::class.java)
            .awaitFirst().age.shouldBe(18)
        log.info("hello world -- end")
    }
}) {
    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }
}