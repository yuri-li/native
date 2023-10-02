package org.fu.demo.controller

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.rsocket.frame.FrameUtil
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactive.awaitSingle
import org.fu.demo.model.User
import org.slf4j.LoggerFactory
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.messaging.rsocket.RSocketRequester
import org.springframework.messaging.rsocket.retrieveFlow
import org.springframework.messaging.rsocket.retrieveMono
import org.springframework.util.MimeType


@SpringBootTest
class ControllerSpec(val requester: RSocketRequester) : StringSpec({
    "request-response" {
        requester
            .route("anonymous.user.profile")
            .retrieveMono<User>()
            .awaitSingle().age.shouldBe(18)
//            .awaitFirst().age.shouldBe(18)
    }
    "request-stream" {
        requester.route("anonymous.user.ids")
            .retrieveFlow<Int>()
            .onEach { log.info("loaded from server: {}", it) }
            .toList().size.shouldBe(5)
    }
}) {
    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }
}