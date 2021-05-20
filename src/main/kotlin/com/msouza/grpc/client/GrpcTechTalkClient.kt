package com.msouza.grpc.client

import com.msouza.grpc.client.request.ClientStreamingRequest
import com.msouza.grpc.client.request.ServerStreamingRequest
import com.msouza.grpc.client.request.UnaryRequest
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import io.grpc.netty.shaded.io.grpc.netty.GrpcSslContexts
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder
import java.io.File

class GrpcTechTalkClient {

    fun runClient() {
        val channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                    .usePlaintext()
                    .build()
        println("gRPC client running")

//        UnaryRequest().doUnaryCall(channel)
        ServerStreamingRequest().doServerStreamingCall(channel)
//        ClientStreamingRequest().doClientStreamingCall(channel)

    }

    fun runSecureClient() {
       val secureChannel = NettyChannelBuilder.forAddress("localhost", 50051)
                .sslContext(GrpcSslContexts.forClient().trustManager(File("ssl/ca.crt")).build())
                .build()

    }

}