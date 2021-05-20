package com.msouza.grpc.client.request

import com.github.javafaker.Faker
import com.msouza.GreetRequest
import com.msouza.GreetResponse
import com.msouza.Greeting
import com.msouza.GrpcTechTalkServiceGrpc
import io.grpc.ManagedChannel

class UnaryRequest {

    fun doUnaryCall(channel: ManagedChannel) {
        // created a greet service client (blocking - synchronous)
        val greetClient = GrpcTechTalkServiceGrpc.newBlockingStub(channel)
        // Unary
        // created a protocol buffer greeting message
        val faker = Faker()
        val greeting = Greeting.newBuilder()
            .setFirstName(faker.name().firstName())
            .setLastName(faker.name().lastName())
            .build()

        // do the same for a GreetRequest
        val greetRequest = GreetRequest.newBuilder()
            .setGreeting(greeting)
            .build()

        // call the RPC and get back a GreetResponse (protocol buffers)
        val greetResponse: GreetResponse = greetClient.greet(greetRequest)
        println(greetResponse.result)
    }
}