package com.msouza.grpc.client.request

import com.github.javafaker.Faker
import com.msouza.GreetRequest
import com.msouza.GreetResponse
import com.msouza.Greeting
import com.msouza.GrpcTechTalkServiceGrpc
import io.grpc.ManagedChannel
import io.grpc.stub.StreamObserver
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class ClientStreamingRequest {

    fun doClientStreamingCall(channel: ManagedChannel) {
        // create an asynchronous client
        val asyncClient = GrpcTechTalkServiceGrpc.newStub(channel)
        val latch = CountDownLatch(1)
        val requestObserver = asyncClient.longGreet(object : StreamObserver<GreetResponse> {
            override fun onNext(value: GreetResponse) {
                // we get a response from the server
                println("Received a response from the server")
                println(value.result)
                // onNext will be called only once
            }

            override fun onError(t: Throwable) {
                // we get an error from the server
            }

            override fun onCompleted() {
                // the server is done sending us data
                // onCompleted will be called right after onNext()
                println("Server has completed sending us something")
                latch.countDown()
            }
        })

        val faker = Faker()

        for (i in 1..20) {
            // streaming message #1
            println("sending message $i")
            requestObserver.onNext(greetRequest(faker.name().firstName()))
            TimeUnit.MILLISECONDS.sleep(500)
        }

        // we tell the server that the client is done sending data
        requestObserver.onCompleted()
        try {
            latch.await(3L, TimeUnit.SECONDS)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    private fun greetRequest(fistName: String) =
        GreetRequest.newBuilder()
            .setGreeting(
                Greeting.newBuilder()
                    .setFirstName(fistName)
                    .build()
            ).build()
}