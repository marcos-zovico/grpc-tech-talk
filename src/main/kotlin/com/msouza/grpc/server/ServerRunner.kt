package com.msouza.grpc.server

fun main(args: Array<String>) {
    val server = GrpcTechTalkServer()
    server.run()
}
