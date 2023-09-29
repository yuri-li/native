import { WellKnownMimeType } from "rsocket-composite-metadata"
import {WebsocketClientTransport} from "rsocket-websocket-client"
import {RSocketConnector} from "rsocket-core"
import {isEmpty} from "@/util/JsonExtension.ts"

class ServerOptions {
    protocol?: "ws"|"wss" = "ws"
    host?: string = "localhost"
    port: number | undefined
    mappingPath: string | undefined
}
function _buildServerOptions(_options: ServerOptions){
    const model = new ServerOptions()
    if(!isEmpty(_options.protocol)){
        model.protocol = _options.protocol
    }
    if(!isEmpty(_options.host)){
        model.host = _options.host
    }
    if(!isEmpty(_options.port)){
        model.port = _options.port
    }
    if(!isEmpty(_options.mappingPath)){
        model.mappingPath = _options.mappingPath
    }
    return model
}
async function createClient(_options: ServerOptions) {
    const setupOptions = {
        keepAlive: 120000,
        lifetime: 60000,
        dataMimeType: "application/json",
        metadataMimeType: WellKnownMimeType.MESSAGE_RSOCKET_COMPOSITE_METADATA.string,
    }
    const options = _buildServerOptions(_options)
    const transport = new WebsocketClientTransport({
        url: `${options.protocol}://${options.host}:${options.port}/${options.mappingPath}`,
        wsCreator: (url) => new WebSocket(url)
    })
    const client = new RSocketConnector({ setup: setupOptions, transport })
    return await client.connect()
}
export {createClient, }