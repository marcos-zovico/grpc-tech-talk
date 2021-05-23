package com.msouza.grpc.client.request

import com.github.javafaker.Faker
import com.msouza.grpc.GreetRequest
import com.msouza.grpc.GreetResponse
import com.msouza.grpc.Greeting
import com.msouza.grpc.GrpcTechTalkServiceGrpc
import io.grpc.ManagedChannel
import io.grpc.stub.StreamObserver
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class BidirectionalStreamingRequest {

    fun doBidiStreamingCall(channel: ManagedChannel) {

        val asyncClient = GrpcTechTalkServiceGrpc.newStub(channel)
        val latch = CountDownLatch(1)

        // receiving data from server
        val requestObserver: StreamObserver<GreetRequest> =
            asyncClient.greetEveryone(object : StreamObserver<GreetResponse> {
                override fun onNext(value: GreetResponse) {
                    println("Response from server: ${value.result}")
                }

                override fun onError(t: Throwable) {
                    latch.countDown()
                }

                override fun onCompleted() {
                    println("Server is done sending data")
                    latch.countDown()
                }
            })

        // sending data to server
        for (i in 1..20) {
            val name = Faker().name().firstName()
            println("Client request: $name")
            requestObserver.onNext(
                GreetRequest.newBuilder()
                    .setGreeting(
                        Greeting.newBuilder()
                            .setFirstName(name)
                    )
                    .build()
            )
            TimeUnit.SECONDS.sleep(1)
        }

        requestObserver.onCompleted()

        latch.await(3, TimeUnit.SECONDS)
    }
}