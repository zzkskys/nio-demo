package com.example.netty.pack

import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.ChannelInitializer
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioServerSocketChannel
import io.netty.handler.codec.DelimiterBasedFrameDecoder
import io.netty.handler.codec.FixedLengthFrameDecoder
import io.netty.handler.logging.LogLevel
import io.netty.handler.logging.LoggingHandler

fun main() {
//    fixedLengthFrameDecoder()
    delimiterBasedFrameDecoder()
}

fun delimiterBasedFrameDecoder() {
    val boss = NioEventLoopGroup()
    val worker = NioEventLoopGroup()

    try {
        val bootstrap = ServerBootstrap()
            .group(boss, worker)
            .channel(NioServerSocketChannel::class.java)
            .childHandler(object : ChannelInitializer<SocketChannel>() {
                override fun initChannel(ch: SocketChannel) {
                    val delimiter = ch.alloc().buffer().writeBytes("SE".toByteArray())

                    ch.pipeline()
                        .addLast(DelimiterBasedFrameDecoder(1024, delimiter))
                        .addLast(LoggingHandler(LogLevel.DEBUG))
                }
            })

        val channelFuture = bootstrap.bind(8080).sync()
        channelFuture.channel().closeFuture().sync()
    } finally {
        boss.shutdownGracefully()
        worker.shutdownGracefully()
    }
}

fun fixedLengthFrameDecoder() {
    val boss = NioEventLoopGroup()
    val worker = NioEventLoopGroup()

    try {
        val bootstrap = ServerBootstrap()
            .group(boss, worker)
            .channel(NioServerSocketChannel::class.java)
            .childHandler(object : ChannelInitializer<SocketChannel>() {
                override fun initChannel(ch: SocketChannel) {
                    ch.pipeline()
                        .addLast(FixedLengthFrameDecoder(10))
                        .addLast(LoggingHandler(LogLevel.DEBUG))
                }
            })

        val channelFuture = bootstrap.bind(8080).sync()
        channelFuture.channel().closeFuture().sync()
    } finally {
        boss.shutdownGracefully()
        worker.shutdownGracefully()
    }
}