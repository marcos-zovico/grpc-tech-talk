package com.msouza.grpc.server.response

import com.msouza.grpc.GreetRequest
import com.msouza.grpc.GreetResponse
import io.grpc.stub.StreamObserver

class LongGreetResponse {

    fun doResponse(responseObserver: StreamObserver<GreetResponse>) =
        object : StreamObserver<GreetRequest> {
            var result = ""
            override fun onNext(value: GreetRequest) {
                // client sends a message
                result += "Hello " + value.greeting.firstName + "! "
            }

            override fun onError(t: Throwable) {
                // client sends an error
            }

            override fun onCompleted() {
                // client is done
                responseObserver.onNext(
                    GreetResponse.newBuilder()
                        .setResult(result)
                        .build()
                )
                responseObserver.onCompleted()
            }
        }
}