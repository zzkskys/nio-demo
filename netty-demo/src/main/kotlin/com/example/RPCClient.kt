package com.example

import com.example.protocol.MessageCodecSharable
import com.example.protocol.MessageProcotolDecoder
import io.netty.bootstrap.Bootstrap
import io.netty.channel.ChannelInitializer
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioSocketChannel
import io.netty.handler.logging.LogLevel
import io.netty.handler.logging.LoggingHandler

fun main() {
    val group = NioEventLoopGroup()
    val logHandler = LoggingHandler(LogLevel.DEBUG)
    val codec = MessageCodecSharable()

    try {
        val bootstrap = Bootstrap()
        bootstrap
            .channel(NioSocketChannel::class.java)
            .group(group)
            .handler(object : ChannelInitializer<SocketChannel>(){
                override fun initChannel(ch: SocketChannel) {
                    ch
                        .pipeline()
                        .addLast(MessageProcotolDecoder())
                        .addLast(logHandler)
                        .addLast(codec)
                }
            })

        val channel = bootstrap.connect("localhost",8080).sync().channel()
        channel.closeFuture().sync()
    }finally {
        group.shutdownGracefully()
    }
}