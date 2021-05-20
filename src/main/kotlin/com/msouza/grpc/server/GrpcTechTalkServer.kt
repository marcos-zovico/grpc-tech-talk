package com.msouza.grpc.server

import io.grpc.ServerBuilder
import java.io.File

class GrpcTechTalkServer {

    fun run() {
        val server = getServer()
//        val server = getSecureServer()

        server.start()
        println("gRPC Server running")

        Runtime.getRuntime().addShutdownHook(Thread {
            println("Received Shutdown Request")
            server.shutdown()
            println("Successfully stopped the server")
        })
        server.awaitTermination()
    }

    private fun getSecureServer() = ServerBuilder.forPort(50051)
            .addService(GrpcTechTalkServiceImpl())
            .useTransportSecurity(
                    File("ssl/server.crt"),
                    File("ssl/server.pem")
            )
            .build();

    private fun getServer() = ServerBuilder.forPort(50051)
            .addService(GrpcTechTalkServiceImpl())
            .build()

}