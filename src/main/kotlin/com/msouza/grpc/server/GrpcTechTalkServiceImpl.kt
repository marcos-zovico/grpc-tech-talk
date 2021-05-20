package com.msouza.grpc.server

import com.msouza.GreetRequest
import com.msouza.GreetResponse
import com.msouza.Greeting
import com.msouza.GrpcTechTalkServiceGrpc
import com.msouza.grpc.server.response.LongGreetResponse
import io.grpc.stub.StreamObserver
import java.util.concurrent.TimeUnit

class GrpcTechTalkServiceImpl : GrpcTechTalkServiceGrpc.GrpcTechTalkServiceImplBase() {

    override fun greet(request: GreetRequest, responseObserver: StreamObserver<GreetResponse>) {

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

    override fun greetManyTimes(request: GreetRequest, responseObserver: StreamObserver<GreetResponse>) {
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


    override fun longGreet(responseObserver: StreamObserver<GreetResponse>): StreamObserver<GreetRequest> =
        LongGreetResponse().doResponse(responseObserver)

    override fun greetEveryone(responseObserver: StreamObserver<GreetResponse>): StreamObserver<GreetRequest> {
        return super.greetEveryone(responseObserver)
    }

    override fun greetWithDeadline(request: GreetRequest, responseObserver: StreamObserver<GreetResponse>) {
        super.greetWithDeadline(request, responseObserver)
    }

    override fun greetWithErrorHandling(request: GreetRequest, responseObserver: StreamObserver<GreetResponse>) {
        super.greetWithErrorHandling(request, responseObserver)
    }

}