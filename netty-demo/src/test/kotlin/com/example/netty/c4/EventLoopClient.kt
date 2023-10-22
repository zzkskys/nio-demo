package com.example.netty.c4

import com.example.netty.c3.EventLoopClient
import io.netty.bootstrap.Bootstrap
import io.netty.channel.ChannelFutureListener
import io.netty.channel.ChannelInitializer
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioSocketChannel
import io.netty.handler.codec.string.StringEncoder
import org.slf4j.LoggerFactory
import java.net.InetSocketAddress

/**
 *
 * @since : 2023/10/19
 * @author zzk
 */
class EventLoopClient {
    companion object {

        private val log = LoggerFactory.getLogger(EventLoopClient::class.java)

        @JvmStatic
        fun main(args: Array<String>) {
            val channelFuture = Bootstrap()
                    .group(NioEventLoopGroup())
                    .channel(NioSocketChannel::class.java)
                    .handler(object : ChannelInitializer<NioSocketChannel>() {
                        override fun initChannel(ch: NioSocketChannel) {
                            ch.pipeline().addLast(StringEncoder())
                        }
                    })
                    .connect(InetSocketAddress("localhost", 8080))

            //添加监听器，监听连接处理结果
            channelFuture.addListener(ChannelFutureListener {
                it.channel().writeAndFlush("hello world")
                log.debug("{}", it)
            })
        }
    }
}