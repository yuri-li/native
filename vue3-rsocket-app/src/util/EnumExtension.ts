function enumKeys<O extends object, K extends keyof O = keyof O>(obj: O): K[] {
    return Object.keys(obj).filter(k => Number.isNaN(+k)) as K[]
}

function toMap<O extends object>(obj: O): Map<string, string> {
    const newObj = new Map<string, string>()
    enumKeys(obj).forEach(key => {
        const x = key as string
        newObj.set(x, Number.isInteger(obj[key]) ? x : obj[key] as string)
    })
    return newObj
}

function toJson<O extends object>(obj: O) {
    return JSON.stringify(enumKeys(obj))
}

export { toMap, toJson, }