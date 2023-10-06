package org.fu.demo.config

import io.rsocket.exceptions.CustomRSocketException
import jakarta.validation.ConstraintViolationException
import org.slf4j.LoggerFactory
import org.springframework.messaging.handler.MessagingAdviceBean
import org.springframework.messaging.handler.annotation.MessageExceptionHandler
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.method.ControllerAdviceBean


class ControllerAdviceWrapper(val bean: ControllerAdviceBean) : MessagingAdviceBean {
    override fun getOrder(): Int {
        return bean.order
    }

    override fun getBeanType(): Class<*>? {
        return bean.beanType
    }

    override fun resolveBean(): Any {
        return bean.resolveBean()
    }

    override fun isApplicableToBeanType(beanType: Class<*>): Boolean {
        return bean.isApplicableToBeanType(beanType)
    }
}

@ControllerAdvice
class GlobalExceptionHandler {
    @Throws(CustomRSocketException::class)
    @MessageExceptionHandler(ConstraintViolationException::class)
    fun handlerConstraintViolationException(ex: ConstraintViolationException) {
        val model = ex.constraintViolations.first()
        val temp = ValidationException(fieldName = model.propertyPath.toString(), message = model.message!!)
        log.error(temp.toString())
        throw temp.toRSocket()
    }

    @Throws(CustomRSocketException::class)
    @MessageExceptionHandler(BusinessException::class)
    fun handlerBusinessException(ex: BusinessException) {
        log.error(ex.toString())
        throw ex.toRSocket()
    }

    @Throws(CustomRSocketException::class)
    @MessageExceptionHandler(ErrorCodeException::class)
    fun handlerErrorCodeException(ex: ErrorCodeException) {
        log.error(ex.toString())
        throw ex.toRSocket()
    }

    @Throws(CustomRSocketException::class)
    @MessageExceptionHandler
    fun handlerUnknownException(ex: Throwable) {
        val temp = UnknownException(cause = ex)
        log.error(temp.toString())
        throw temp.toRSocket()
    }

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }
}

enum class Reason(val errorCode: Int) {
    ValidationException(0x00000301),
    BusinessException(0x00000302),
    ErrorCodeException(0x00000303),
    UnknownException(0x00000304)
}

data class BusinessException(override val message: String) : RuntimeException(message) {
    override fun toString(): String = "${javaClass.name}: $message"
    fun toRSocket() = CustomRSocketException(
        Reason.BusinessException.errorCode,
        """{"reason":"${Reason.BusinessException}","message":"$message"}"""
    )
}

data class ErrorCodeException(val code: String, override val message: String) : RuntimeException(message) {
    override fun toString(): String = "${javaClass.name}: { code: $code -> message: $message }"
    fun toRSocket() = CustomRSocketException(
        Reason.ErrorCodeException.errorCode,
        """{"reason":"${Reason.ErrorCodeException}","code":"$code","message":"$message"}"""
    )
}

data class ValidationException(val fieldName: String, override val message: String) : RuntimeException(message) {
    override fun toString(): String = "${javaClass.name}: { code: $fieldName -> message: $message }"
    fun toRSocket() = CustomRSocketException(
        Reason.ValidationException.errorCode,
        """{"reason":"${Reason.ValidationException}","fieldName":"$fieldName","message":"$message"}"""
    )
}

data class UnknownException(override val cause: Throwable) : RuntimeException(cause) {
    override fun toString(): String = "${javaClass.name}: Unknown"
    fun toRSocket() = CustomRSocketException(
        Reason.UnknownException.errorCode,
        """{"reason":"${Reason.UnknownException}","message":"Unknown"}"""
    )
}