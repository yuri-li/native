import {
    describe,
    test
} from "vitest"
import Data from "@/assets/app.yml"
import { ErrorCodeException } from "@/config/api/model/globalException.ts"

describe("测试class", () => {
    test("encode to json", () => {
        console.log(Data)
    })
    test("exception", ()=>{
        const exception = new ErrorCodeException("ConnectException", `RSocket connection to localhost://7001 failed`).toError()
        console.log(JSON.stringify(exception))
        console.log(exception.message)
    })
    test("values",()=>{
        const data = null
        console.log(data || "abc")
    })
})