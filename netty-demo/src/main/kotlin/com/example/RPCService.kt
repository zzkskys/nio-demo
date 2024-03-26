package com.example

import com.example.protocol.MessageCodecSharable
import com.example.protocol.MessageProcotolDecoder
import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.ChannelInitializer
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioServerSocketChannel
import io.netty.handler.logging.LogLevel
import io.netty.handler.logging.LoggingHandler

fun main() {
    val boss = NioEventLoopGroup()
    val worker = NioEventLoopGroup()
    val logHandler = LoggingHandler(LogLevel.DEBUG)
    val messageCodec = MessageCodecSharable()

    try {
        val bootstrap = ServerBootstrap()
        bootstrap
            .channel(NioServerSocketChannel::class.java)
            .group(boss, worker)
            .childHandler(object : ChannelInitializer<SocketChannel>() {
                override fun initChannel(ch: SocketChannel) {
                    ch
                        .pipeline()
                        .addLast(MessageProcotolDecoder())
                        .addLast(logHandler)
                        .addLast(messageCodec)
                }
            })
        val channel = bootstrap.bind(8080).sync().channel()
        channel.closeFuture().sync()
    } finally {
        boss.shutdownGracefully()
        worker.shutdownGracefully()
    }
}