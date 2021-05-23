package com.msouza.grpc.server

import com.msouza.grpc.GreetRequest
import com.msouza.grpc.GreetResponse
import com.msouza.grpc.GrpcTechTalkServiceGrpc
import com.msouza.grpc.server.response.*
import io.grpc.stub.StreamObserver
import com.msouza.grpc.server.response.GreetResponse as GreetUnaryResponse

class GrpcTechTalkServiceImpl : GrpcTechTalkServiceGrpc.GrpcTechTalkServiceImplBase() {

    override fun greet(request: GreetRequest, responseObserver: StreamObserver<GreetResponse>) =
        GreetUnaryResponse().doResponse(request, responseObserver)

    override fun greetManyTimes(request: GreetRequest, responseObserver: StreamObserver<GreetResponse>) =
        GreetManyTimesResponse().doResponse(request, responseObserver)

    override fun longGreet(responseObserver: StreamObserver<GreetResponse>) =
        LongGreetResponse().doResponse(responseObserver)

    override fun greetEveryone(responseObserver: StreamObserver<GreetResponse>) =
        GreetEveryoneResponse().doResponse(responseObserver)

    override fun greetWithDeadline(request: GreetRequest, responseObserver: StreamObserver<GreetResponse>) =
        GreetWithDeadlineResponse().doResponse(request, responseObserver)

    override fun greetWithErrorHandling(request: GreetRequest, responseObserver: StreamObserver<GreetResponse>) =
        GreetWithErrorHandlingResponse().doResponse(request, responseObserver)

}