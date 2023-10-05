import {
    describe,
    test
} from "vitest"
import Data from "@/assets/app.yml"
import { ErrorCodeException } from "@/config/api/model/globalException.ts"
import { TransferDto } from "@/components/transfer/model.ts"
import { requestResponse } from "@/config/api/rsocket"

describe("测试class", () => {
    test("encode to json", () => {
        console.log(Data)
    })
    test("exception", () => {
        const exception = new ErrorCodeException("ConnectException", `RSocket connection to localhost://7001 failed`).toError()
        console.log(JSON.stringify(exception))
        console.log(exception.message)
    })
    test("values", () => {
        const data = null
        console.log(data || "abc")
    })
    test("json", () => {
        const dto = new TransferDto(25)
        console.log(JSON.stringify(dto))
    })
    test("fire and forget", async () => {
        await requestResponse("anonymous.cash.transfer", new TransferDto(25))
    })
})