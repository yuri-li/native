package org.fu.demo.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.messaging.rsocket.RSocketRequester
import org.springframework.messaging.rsocket.RSocketStrategies
import java.net.URI


@Configuration
class RSocketConfig {
    @Bean
    fun requester(
        builder: RSocketRequester.Builder,
        rSocketStrategies: RSocketStrategies,
        @Value("\${spring.rsocket.server.port}") port: Int,
        @Value("\${spring.rsocket.server.mapping-path}") mappingPath: String,
    ): RSocketRequester =
        builder
            .rsocketStrategies(rSocketStrategies)
            .dataMimeType(MediaType.APPLICATION_JSON).websocket(URI.create("ws://localhost:${port}/${mappingPath}"))
}