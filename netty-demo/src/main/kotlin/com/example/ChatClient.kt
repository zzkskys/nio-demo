package com.example

import com.example.client.ClientLoginHandler
import com.example.message.PingMessage
import com.example.protocol.MessageCodec
import com.example.protocol.MessageProcotolDecoder
import io.netty.bootstrap.Bootstrap
import io.netty.channel.ChannelDuplexHandler
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInitializer
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioSocketChannel
import io.netty.handler.logging.LogLevel
import io.netty.handler.logging.LoggingHandler
import io.netty.handler.timeout.IdleState
import io.netty.handler.timeout.IdleStateEvent
import io.netty.handler.timeout.IdleStateHandler

fun main() {
    val group = NioEventLoopGroup()
    val logHandler = LoggingHandler(LogLevel.DEBUG)
    val messageCodec = MessageCodec()

    try {
        val bootstrap = Bootstrap()
        bootstrap.channel(NioSocketChannel::class.java)
            .group(group)
            .handler(object : ChannelInitializer<SocketChannel>() {
                override fun initChannel(ch: SocketChannel) {
                    ch.pipeline()
                        .addLast(MessageProcotolDecoder())
                        .addLast(logHandler)
                        .addLast(messageCodec)
                        .addLast(ClientLoginHandler())
                }
            })

        val channelFuture = bootstrap.connect("localhost", 8080)
        channelFuture.channel().closeFuture().sync()
    } finally {
        group.shutdownGracefully()
    }
}