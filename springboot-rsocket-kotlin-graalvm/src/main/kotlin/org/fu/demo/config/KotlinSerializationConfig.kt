package org.fu.demo.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.codec.json.KotlinSerializationJsonDecoder
import org.springframework.http.codec.json.KotlinSerializationJsonEncoder
import org.springframework.messaging.rsocket.RSocketStrategies

@Configuration
class KotlinSerializationConfig {
    @Bean
    fun rSocketStrategies() = RSocketStrategies.builder()
        .encoders { it.add(KotlinSerializationJsonEncoder())}
        .decoders { it.add(KotlinSerializationJsonDecoder())}
        .build()
}