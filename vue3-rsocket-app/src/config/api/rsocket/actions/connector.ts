// noinspection JSUnresolvedReference

import { WellKnownMimeType } from "rsocket-composite-metadata"
import { WebsocketClientTransport } from "rsocket-websocket-client"
import { RSocketConnector } from "rsocket-core"
import ServerConfig from "@/assets/app.yml"
import { ErrorCodeException } from "@/config/api/model/globalException"

const connector = await createClient()

async function createClient() {
    const setupOptions = {
        keepAlive: 120000,
        lifetime: 60000,
        dataMimeType: "application/json",
        metadataMimeType: WellKnownMimeType.MESSAGE_RSOCKET_COMPOSITE_METADATA.string,
    }
    const url = _toUrl()
    const transport = new WebsocketClientTransport({
        url,
        wsCreator: (_url) => {
            const ws = new WebSocket(_url)
            ws.onopen = (_) =>{
                console.log("创建链接")
            }
            ws.onerror = (_) => {
                throw new ErrorCodeException("ConnectException", `RSocket connection to ${url} failed`)
            }
            return ws
        }
    })
    const connector = new RSocketConnector({setup: setupOptions, transport})
    return await connector.connect()
}

function _toUrl() {
    const config = ServerConfig.server.rsocket
    return `${config.protocol}://${config.host}:${config.port}/${config.mappingPath}`
}

export { connector as rSocket, }