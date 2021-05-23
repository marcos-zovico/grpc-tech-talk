package com.msouza.grpc.server.response

import com.msouza.grpc.GreetRequest
import com.msouza.grpc.GreetResponse
import com.msouza.grpc.Greeting
import io.grpc.stub.StreamObserver

class GreetResponse {

    fun doResponse(request: GreetRequest, responseObserver: StreamObserver<GreetResponse>) {

        val greeting: Greeting = request.greeting

        // create the response
        val result = "Hello ${greeting.firstName} ${greeting.lastName}"
        val response = GreetResponse.newBuilder()
            .setResult(result)
            .build()

        // send the response
        responseObserver.onNext(response)
        // complete the RPC call
        responseObserver.onCompleted()
    }
}