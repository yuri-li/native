import { Payload as _Payload, } from "rsocket-core/dist/RSocket"
import { requestResponse } from "@/config/api/rsocket/actions/requestResponse"
import { StreamResponse as _StreamResponse, } from "@/config/api/rsocket/model/rsocketTypes"
import { requestStream, } from "@/config/api/rsocket/actions/requestStream"
import { fireAndForget, } from "@/config/api/rsocket/actions/fireAndForget"


export type StreamResponse = _StreamResponse
export type Payload = _Payload
export {
    requestResponse,
    requestStream,
    fireAndForget,
}