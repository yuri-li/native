// noinspection SuspiciousTypeOfGuard

function replacer(_: string, value?: never){
    return isEmpty(value)? undefined : value
}
function isEmpty(value?: any){
    return  value === undefined ||
        value === null ||
        (typeof value === "object" && Object.keys(value).length === 0) ||
        (typeof value === "string" && (value as string).trim().length === 0)
}

const reISO = /^(\d{4})-(\d{2})-(\d{2})T(\d{2}):(\d{2}):(\d{2}(?:\.\d*))(?:Z|(\+|-)([\d|:]*))?$/
function dateParser(_: string, value?: never) {
    if (!isEmpty(value) && typeof value === "string") {
        if (reISO.exec(value))
            return new Date(value)
    }
    return value
}
export { replacer, dateParser, isEmpty}