import {
    fireAndForgetEvent,
} from "@/config/api/rsocket/model/rsocketTypes"
import { rSocket } from "@/config/api/rsocket/actions/connector"
import { buildPayload, } from "@/config/api/rsocket/actions/BufferUtil"

function fireAndForget(route: string, data: any | null = null) {
    return new Promise<null>((resolve, reject) =>
        rSocket.fireAndForget(buildPayload(route, data), fireAndForgetEvent(resolve, reject))
    )
}

export { fireAndForget }