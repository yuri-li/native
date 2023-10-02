import { requestResponseEvent, } from "@/config/api/rsocket/model/rsocketTypes"
import { rSocket } from "@/config/api/rsocket/actions/connector"
import { buildPayload, } from "@/config/api/rsocket/actions/BufferUtil"

function requestResponse<R>(route: string, data: any | null = null) {
    return new Promise<R>((resolve, reject) =>
        rSocket.requestResponse(buildPayload(route, data), requestResponseEvent(resolve, reject))
    )
}

export { requestResponse }