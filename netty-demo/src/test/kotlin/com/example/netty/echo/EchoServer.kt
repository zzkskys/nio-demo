package com.example.netty.echo

import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandlerAdapter
import io.netty.channel.ChannelInitializer
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioServerSocketChannel
import io.netty.handler.codec.string.StringDecoder

fun main() {
    ServerBootstrap()
        .group(NioEventLoopGroup())
        .channel(NioServerSocketChannel::class.java)
        .childHandler(object : ChannelInitializer<SocketChannel>() {
            override fun initChannel(ch: SocketChannel) {
                ch.pipeline()
                    .addLast(StringDecoder())
                    .addLast(EchoChildHandler())
            }

        })
        .bind(8080)
}

/**
 * 服务器 handler,将获取的内容响应
 */
class EchoChildHandler : ChannelInboundHandlerAdapter() {
    override fun channelRead(ctx: ChannelHandlerContext, msg: Any) {
        if (msg !is String) throw RuntimeException("msg 不是 String 类型")
        println("客户端发出了 : $msg,正在回应......")

        val response = ctx.alloc().buffer()
        response.writeBytes(msg.toByteArray())
        ctx.writeAndFlush(response)
        super.channelRead(ctx, msg)
    }
}