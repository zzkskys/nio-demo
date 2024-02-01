package com.example.netty.pack

import cn.hutool.core.util.RandomUtil
import io.netty.bootstrap.Bootstrap
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandlerAdapter
import io.netty.channel.ChannelInitializer
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioSocketChannel

fun main() {
    for (i in 0..9) {
        clientSend()
    }
    println("finish")
}

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
                        .addLast(object : ChannelInboundHandlerAdapter() {
                            override fun channelActive(ctx: ChannelHandlerContext) {
                                val str = RandomUtil.randomStringWithoutStr(10, "SE") + "SE"
                                val buffer = ctx.alloc().buffer()
                                buffer.writeBytes(str.toByteArray())
                                ctx.writeAndFlush(buffer)
                                ctx.channel().close()
                            }
                        })
                }
            })
        val channelFuture = bootstrap.connect("localhost", 8080)
        channelFuture.channel().closeFuture().sync()
    } finally {
        group.shutdownGracefully()
    }
}