package com.msouza.grpc.server.response

import com.msouza.grpc.GreetRequest
import com.msouza.grpc.GreetResponse
import io.grpc.stub.StreamObserver

class GreetEveryoneResponse {

    fun doResponse(responseObserver: StreamObserver<GreetResponse>) =
        object : StreamObserver<GreetRequest> {
            override fun onNext(value: GreetRequest) {
                val result = "Hello ${value.greeting.firstName}"
                val response = GreetResponse.newBuilder()
                    .setResult(result)
                    .build()
                responseObserver.onNext(response)
            }

            override fun onError(t: Throwable?) {
                // do nothing
            }

            override fun onCompleted() {
                responseObserver.onCompleted()
            }
        }
}