package org.fu.demo.controller

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.fu.demo.model.User
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.messaging.rsocket.RSocketRequester
import org.springframework.messaging.rsocket.retrieveFlow

@SpringBootTest
class KomapperSpec(val requester: RSocketRequester) : StringSpec({
    "create table & insert & select"{
        requester.route("anonymous.user.initDB")
            .send()
            .awaitSingleOrNull()

        requester.route("anonymous.user.add")
            .send()
            .awaitSingleOrNull()

        requester.route("anonymous.user.findAll")
            .retrieveFlow<User>()
            .toList().size.shouldBe(4)
    }
})