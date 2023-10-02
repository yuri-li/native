enum Reason {
    ValidationException = 0x00000301,
    BusinessException = 0x00000302,
    ErrorCodeException = 0x00000303,
    UnknownException = 0x00000304
}

class BusinessException extends Error {
    reason: Reason

    constructor(_message: string) {
        super(_message)
        this.name = this.constructor.name
        this.reason = Reason.BusinessException
    }

    toString() {
        return `{"reason":${this.reason.valueOf()},"name":"${this.name}","message":"${this.message}"}`
    }

    toError() {
        return {name: this.name, message: this.toString()} as Error
    }
}

class ErrorCodeException extends Error {
    reason: Reason
    code: string

    constructor(_code: string, _message: string) {
        super(_message)
        this.code = _code
        this.name = this.constructor.name
        this.reason = Reason.ErrorCodeException
    }

    toString(): string {
        return `{"reason":${this.reason.valueOf()},"name":"${this.name}","code":"${this.code}","message":"${this.message}"}`
    }

    toError() {
        return {name: this.name, message: this.toString()} as Error
    }
}

class UnknownException extends Error {
    reason: Reason

    constructor(cause: Error) {
        super(cause.message)
        this.name = this.constructor.name
        this.reason = Reason.UnknownException
    }

    toString(): string {
        return `{"reason":${this.reason.valueOf()},"name":"${this.name}","message":"Unknown"}`
    }

    toError() {
        return {name: this.name, message: this.toString()} as Error
    }
}

class ValidationException extends Error {
    reason: Reason
    fieldName: string

    constructor(_fieldName: string, _message: string) {
        super(_message)
        this.fieldName = _fieldName
        this.name = this.constructor.name
        this.reason = Reason.ValidationException
    }

    toString() {
        return `{"reason":${this.reason.valueOf()},"name":"${this.name}","fieldName":"${this.fieldName}","message":"${this.message}"}`
    }

    toError() {
        return {name: this.name, message: this.toString()} as Error
    }
}

export {
    Reason,
    BusinessException,
    ErrorCodeException,
    UnknownException,
    ValidationException,
}