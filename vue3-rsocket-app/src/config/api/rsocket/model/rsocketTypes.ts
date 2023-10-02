import {
    Requestable,
    Cancellable,
    OnExtensionSubscriber,
    OnNextSubscriber,
    Payload
} from "rsocket-core/dist/RSocket"

function requestResponseEvent<R>(resolve: (value: (PromiseLike<R> | R)) => void, reject: (reason?: any) => void) {
    return {
        onNext(payload: Payload, isComplete: boolean): void {
            console.log(`payload[data: ${payload.data}; metadata: ${payload.metadata}] | ${isComplete}`)
            resolve(payload.data as R)
        },
        onError(error: Error): void {
            reject(error)
        },
        onComplete(): void {
        },
        onExtension(): void {
            console.log("------onExtension------")
        },
    }
}

export interface OnCompleteSubscriber {
    onComplete(): void;
}

export type StreamResponse = Requestable & Cancellable & OnExtensionSubscriber
export type StreamResponseEvent = OnCompleteSubscriber & OnNextSubscriber

export {
    requestResponseEvent
}