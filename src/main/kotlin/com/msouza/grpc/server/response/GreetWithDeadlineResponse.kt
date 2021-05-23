package com.msouza.grpc.server.response

import com.msouza.grpc.GreetRequest
import com.msouza.grpc.GreetResponse
import io.grpc.Context
import io.grpc.stub.StreamObserver
import java.util.concurrent.TimeUnit

class GreetWithDeadlineResponse {

    fun doResponse(request: GreetRequest, responseObserver: StreamObserver<GreetResponse>) {

        val current = Context.current()

        for (i in 0..2) {
            if (!current.isCancelled) {
                println("sleep for 100 ms")
                TimeUnit.MILLISECONDS.sleep(100)
            } else {
                return
            }
        }

        println("send response")
        responseObserver.onNext(
            GreetResponse.newBuilder()
                .setResult("hello " + request.greeting.firstName)
                .build()
        )

        responseObserver.onCompleted()
    }
}