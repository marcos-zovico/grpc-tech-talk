package com.msouza.grpc.client.request

import com.github.javafaker.Faker
import com.msouza.grpc.GreetRequest
import com.msouza.grpc.GreetResponse
import com.msouza.grpc.Greeting
import com.msouza.grpc.GrpcTechTalkServiceGrpc
import io.grpc.ManagedChannel
import io.grpc.Status
import io.grpc.StatusRuntimeException
import java.util.concurrent.TimeUnit

class UnaryWithDeadlineRequest {

    fun doUnaryCallWithDeadline(channel: ManagedChannel) {
        val blockingStub = GrpcTechTalkServiceGrpc.newBlockingStub(channel)

        // first call (1000 ms deadline) should pass
        sendRequestWithDeadline(1000, blockingStub)

        // second call (100 ms deadline) should fail
        sendRequestWithDeadline(100, blockingStub)
    }

    private fun sendRequestWithDeadline(
        duration: Long,
        blockingStub: GrpcTechTalkServiceGrpc.GrpcTechTalkServiceBlockingStub
    ) {
        try {

            println("\nSending a request with a deadline of $duration ms")
            val response: GreetResponse =
                blockingStub.withDeadlineAfter(duration, TimeUnit.MILLISECONDS)
                    .greetWithDeadline(
                        GreetRequest.newBuilder()
                            .setGreeting(
                                Greeting.newBuilder()
                                    .setFirstName(Faker().name().firstName())
                            ).build()
                    )

            println(response.result)

        } catch (e: StatusRuntimeException) {
            if (e.status.code == Status.Code.DEADLINE_EXCEEDED) {
                println("Deadline has been exceeded, we don't want the response")
                println(e)
            } else {
                println(e.stackTrace)
            }
        }
    }
}