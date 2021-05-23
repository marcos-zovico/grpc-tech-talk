package com.msouza.grpc.server.response

import com.msouza.grpc.GreetRequest
import com.msouza.grpc.GreetResponse
import io.grpc.Status
import io.grpc.stub.StreamObserver
import org.apache.commons.lang3.StringUtils.isEmpty

class GreetWithErrorHandlingResponse {

    fun doResponse(request: GreetRequest, responseObserver: StreamObserver<GreetResponse>) {

        val firstName = request.greeting.firstName
        val lastName = request.greeting.lastName

        if (isEmpty(firstName) || isEmpty(lastName)) {
            responseObserver.onError(
                Status.INVALID_ARGUMENT
                    .withDescription("Both first and last name are required")
                    .asRuntimeException())
        }

        println("send response")
        responseObserver.onNext(
            GreetResponse.newBuilder()
                .setResult("hello $firstName, $lastName")
                .build()
        )

        responseObserver.onCompleted()
    }
}