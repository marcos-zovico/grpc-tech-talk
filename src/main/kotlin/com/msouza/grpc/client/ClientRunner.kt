package com.msouza.grpc.client

fun main(args: Array<String>) {
    val client = GrpcTechTalkClient()
    client.runClient()
}
