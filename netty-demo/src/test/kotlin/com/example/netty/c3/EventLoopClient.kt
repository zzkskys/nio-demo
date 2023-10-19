package com.example.netty.c3

import io.netty.bootstrap.Bootstrap
import io.netty.channel.ChannelInitializer
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioSocketChannel
import io.netty.handler.codec.string.StringEncoder
import java.net.InetSocketAddress

/**
 *
 * @since : 2023/10/19
 * @author zzk
 */
class EventLoopClient {
    companion object{
        @JvmStatic
        fun main(args: Array<String>) {
            Bootstrap()
                .group(NioEventLoopGroup())
                .channel(NioSocketChannel::class.java)
                .handler(object : ChannelInitializer<NioSocketChannel>() {
                    override fun initChannel(ch: NioSocketChannel) {
                        ch.pipeline().addLast(StringEncoder())
                    }
                })
                .connect(InetSocketAddress("localhost", 8080))
                .sync()
                .channel()
                .writeAndFlush("hello world")
        }
    }
}