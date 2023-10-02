import { Payload, } from "rsocket-core/dist/RSocket"
import { StreamResponse, StreamResponseEvent, } from "@/config/api/rsocket/model/rsocketTypes"
import { buildPayload, } from "@/config/api/rsocket/actions/BufferUtil"
import { rSocket } from "@/config/api/rsocket/actions/connector"

async function requestStream(route: string, data: any | null, initialRequestN: number, responderStream: StreamResponseEvent) {
    return new Promise<StreamResponse>((resolve, reject) => {
            resolve(rSocket.requestStream(
                buildPayload(route, data),
                initialRequestN,
                {
                    onNext(payload: Payload, isComplete: boolean) {
                        responderStream.onNext(payload, isComplete)
                    },
                    onComplete() {
                        responderStream.onComplete()
                    },
                    onError(error: Error): void {
                        reject(error)
                    },
                    onExtension(): void {
                        console.log("------onExtension------")
                    },
            }))
        }
    )
}

export { requestStream }