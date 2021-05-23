package com.msouza.grpc.client

import com.msouza.grpc.client.request.*
import io.grpc.ManagedChannelBuilder

class GrpcTechTalkClient {

    fun runClient() {
        val channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                    .usePlaintext()
                    .build()
        println("gRPC client running")

//        UnaryRequest().doUnaryCall(channel)
//        ServerStreamingRequest().doServerStreamingCall(channel)
//        ClientStreamingRequest().doClientStreamingCall(channel)
//        BidirectionalStreamingRequest().doBidiStreamingCall(channel)
        UnaryWithDeadlineRequest().doUnaryCallWithDeadline(channel)
//      UnaryWithErrorHandlingRequest().doUnaryCallWithErrorHandling(channel)

    }
}