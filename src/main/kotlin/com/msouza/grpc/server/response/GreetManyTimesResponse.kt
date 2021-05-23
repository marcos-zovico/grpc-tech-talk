package com.msouza.grpc.server.response

import com.msouza.grpc.GreetRequest
import com.msouza.grpc.GreetResponse
import io.grpc.stub.StreamObserver
import java.util.concurrent.TimeUnit

class GreetManyTimesResponse {

    fun doResponse(request: GreetRequest, responseObserver: StreamObserver<GreetResponse>) {

        val firstName = request.greeting.firstName

        println("Received message $firstName")

        for (i in 1..10) {
            val result = "Hello $firstName, response number: $i"
            val response: GreetResponse = GreetResponse.newBuilder()
                .setResult(result)
                .build()
            responseObserver.onNext(response)
            TimeUnit.MILLISECONDS.sleep(500)
        }

        responseObserver.onCompleted()
    }
}