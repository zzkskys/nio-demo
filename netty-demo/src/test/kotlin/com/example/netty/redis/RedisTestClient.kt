package com.example.netty.redis

import cn.hutool.core.util.RandomUtil
import io.netty.bootstrap.Bootstrap
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandlerAdapter
import io.netty.channel.ChannelInitializer
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioSocketChannel
import io.netty.handler.logging.LogLevel
import io.netty.handler.logging.LoggingHandler


fun main() {



}

//表示换行
val line = byteArrayOf(13,10)

/**
 * 使用短连接法解决黏包
 */
fun clientSend() {
    val group = NioEventLoopGroup()
    try {
        val bootstrap = Bootstrap()
            .group(group)
            .channel(NioSocketChannel::class.java)
            .handler(object : ChannelInitializer<NioSocketChannel>() {
                override fun initChannel(ch: NioSocketChannel) {
                    ch.pipeline()
                        .addLast(LoggingHandler(LogLevel.DEBUG))
                        .addLast()
                }
            })
        val channelFuture = bootstrap.connect("localhost", 8080)
        channelFuture.channel().closeFuture().sync()
    } finally {
        group.shutdownGracefully()
    }
}

class RedisSendInboundHandler : ChannelInboundHandlerAdapter(){
    override fun channelActive(ctx: ChannelHandlerContext) {
        val buf = ctx.alloc().buffer()
        buf.writeBytes("*3".toByteArray())
        buf.writeBytes(line)
        buf.writeBytes("$3".toByteArray())
        buf.writeBytes(line)
        buf.writeBytes("set".toByteArray())
        buf.writeBytes(line)
        buf.writeBytes("$4".toByteArray())
        buf.writeBytes(line)
        buf.writeBytes("name".toByteArray())
    }
}