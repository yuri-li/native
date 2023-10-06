package org.fu.demo.controller

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.rsocket.exceptions.CustomRSocketException
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.fu.demo.config.Reason
import org.fu.demo.model.TransferDto
import org.fu.demo.model.User
import org.slf4j.LoggerFactory
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.messaging.rsocket.RSocketRequester
import org.springframework.messaging.rsocket.retrieveFlow
import org.springframework.messaging.rsocket.retrieveFlux
import org.springframework.messaging.rsocket.retrieveMono
import reactor.test.StepVerifier


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
    "request-stream & 背压" {
        StepVerifier.create(requester.route("anonymous.user.ids").retrieveFlux<Int>())
            .thenRequest(2)
            .expectNext(0, 1)
            .thenRequest(3)
            .expectNext(2, 3, 4)
            .expectComplete()
            .verify()
    }
    "fire and forget" {
        requester.route("anonymous.cash.transfer")
            .data(TransferDto(amount = 15))
            .send()
            .awaitSingleOrNull()
    }
    "exception handler" {
        val amount = 25
        val exception = shouldThrow<CustomRSocketException> {
            requester.route("anonymous.cash.transfer")
                .data(TransferDto(amount = 25))
                .retrieveMono<Void>()
                .awaitSingleOrNull()
        }
        exception.errorCode().shouldBe(Reason.ValidationException.errorCode)
        exception.message.shouldBe("""{"reason":"ValidationException","fieldName":"transfer.dto.amount","message":"转账金额 $amount > 20，账户余额不足"}""")
    }
}) {
    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }
}