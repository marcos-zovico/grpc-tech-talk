package com.msouza.grpc.client.request

import com.github.javafaker.Faker
import com.msouza.grpc.GreetRequest
import com.msouza.grpc.GreetResponse
import com.msouza.grpc.Greeting
import com.msouza.grpc.GrpcTechTalkServiceGrpc
import io.grpc.ManagedChannel

class UnaryRequest {

    fun doUnaryCall(channel: ManagedChannel) {

        val greetClient = GrpcTechTalkServiceGrpc.newBlockingStub(channel)

        val greeting = Greeting.newBuilder()
            .setFirstName(Faker().name().firstName())
            .setLastName(Faker().name().lastName())
            .build()

        val greetRequest = GreetRequest.newBuilder()
            .setGreeting(greeting)
            .build()

        val greetResponse: GreetResponse = greetClient.greet(greetRequest)
        println(greetResponse.result)
    }
}