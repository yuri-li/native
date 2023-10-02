import {
    encodeAndAddWellKnownMetadata,
    encodeBearerAuthMetadata,
    encodeCompositeMetadata,
    encodeRoute,
    WellKnownMimeType
} from "rsocket-composite-metadata"
import { isEmpty } from "@/util/JsonExtension"
import { Payload, } from "rsocket-core/dist/RSocket"

function buildMetadata(route: string) {
    const routeMetadata = encodeRoute(route)
    if(route.startsWith("anonymous.")){
        return encodeAndAddWellKnownMetadata(
            Buffer.alloc(0),
            WellKnownMimeType.MESSAGE_RSOCKET_ROUTING,
            routeMetadata
        )
    }
    const token = localStorage.getItem("token")!!
    return encodeCompositeMetadata([
        [WellKnownMimeType.MESSAGE_RSOCKET_ROUTING, routeMetadata],
        [WellKnownMimeType.MESSAGE_RSOCKET_AUTHENTICATION, encodeBearerAuthMetadata(token)],
        // ["x.rsocket.authentication.bearer.v0", encodeBearerAuthMetadata("")],
    ])
}
function buildData(data: any | null) {
    if (isEmpty(data)) {
        return null
    }
    return Buffer.from(JSON.stringify(data), "utf8")
}

function buildPayload(route: string, data: any | null){
    return {
        data: buildData(data),
        metadata: buildMetadata(route),
    } as Payload
}
export {
    buildPayload
}