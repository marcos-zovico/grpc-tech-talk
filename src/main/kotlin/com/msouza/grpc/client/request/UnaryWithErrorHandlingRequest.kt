package com.msouza.grpc.client.request

import com.github.javafaker.Faker
import com.msouza.grpc.GreetRequest
import com.msouza.grpc.GreetResponse
import com.msouza.grpc.Greeting
import com.msouza.grpc.GrpcTechTalkServiceGrpc
import io.grpc.ManagedChannel
import io.grpc.Status
import io.grpc.StatusRuntimeException

class UnaryWithErrorHandlingRequest {

    fun doUnaryCallWithErrorHandling(channel: ManagedChannel) {
        val blockingStub = GrpcTechTalkServiceGrpc.newBlockingStub(channel)

        try {
            val response: GreetResponse =
                blockingStub.greetWithErrorHandling(
                        GreetRequest.newBuilder()
                            .setGreeting(
                                Greeting.newBuilder()
                                    .setFirstName(Faker().name().firstName())
                            ).build()
                    )

            println(response.result)

        } catch (e: StatusRuntimeException) {
            if (e.status.code == Status.Code.INVALID_ARGUMENT) {
                println("Validation failed, we don't want the response")
                println(e)
            } else {
                println(e.stackTrace)
            }
        }
    }

}