package com.msouza.grpc.client.request

import com.github.javafaker.Faker
import com.msouza.grpc.GreetRequest
import com.msouza.grpc.Greeting
import com.msouza.grpc.GrpcTechTalkServiceGrpc
import io.grpc.ManagedChannel

class ServerStreamingRequest {

    fun doServerStreamingCall(channel: ManagedChannel) {

        val greetClient = GrpcTechTalkServiceGrpc.newBlockingStub(channel)

        // Server Streaming
        // we prepare the request
        val greetManyTimesRequest: GreetRequest = GreetRequest.newBuilder()
            .setGreeting(Greeting.newBuilder().setFirstName(Faker().name().firstName()))
            .build()

        // we stream the responses (in a blocking manner)
        greetClient.greetManyTimes(greetManyTimesRequest)
            .forEachRemaining {
                    greetManyTimesResponse -> println(greetManyTimesResponse.result)
            }

    }
}