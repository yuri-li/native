package org.fu.demo.config

import jakarta.validation.Validation
import jakarta.validation.Validator
import org.hibernate.validator.HibernateValidator
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.codec.json.KotlinSerializationJsonDecoder
import org.springframework.http.codec.json.KotlinSerializationJsonEncoder
import org.springframework.messaging.rsocket.RSocketStrategies
import org.springframework.messaging.rsocket.annotation.support.RSocketMessageHandler
import org.springframework.web.method.ControllerAdviceBean

@Configuration
class RSocketConfiguration {

    @Bean
    fun rSocketStrategies() = RSocketStrategies.builder()
        .encoders { it.add(KotlinSerializationJsonEncoder()) }
        .decoders { it.add(KotlinSerializationJsonDecoder()) }
        .build()

    @Bean
    fun validator(): Validator = Validation
        .byProvider(HibernateValidator::class.java)
        .configure()
        .failFast(true)
        .buildValidatorFactory().validator

    @Bean
    fun messageHandler(
        context: ApplicationContext,
        rSocketStrategies: RSocketStrategies,
    ): RSocketMessageHandler {
        val messageHandler = RSocketMessageHandler()
        messageHandler.setRSocketStrategies(rSocketStrategies)
        ControllerAdviceBean.findAnnotatedBeans(context).forEach { controllerAdviceBean ->
            messageHandler.registerMessagingAdvice(ControllerAdviceWrapper(controllerAdviceBean))
        }

        return messageHandler
    }
}