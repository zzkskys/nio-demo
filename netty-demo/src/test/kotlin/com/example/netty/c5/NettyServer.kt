package com.example.netty.c5

import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandlerAdapter
import io.netty.channel.ChannelInitializer
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioServerSocketChannel
import io.netty.channel.socket.nio.NioSocketChannel
import io.netty.handler.codec.string.StringDecoder
import org.slf4j.LoggerFactory

class NettyServer {
    companion object {

        private val log = LoggerFactory.getLogger(NettyServer::class.java)

        @JvmStatic
        fun main(args: Array<String>) {
            ServerBootstrap()
                .group(NioEventLoopGroup())
                .channel(NioServerSocketChannel::class.java)
                .childHandler(
                    object : ChannelInitializer<NioSocketChannel>() {
                        override fun initChannel(ch: NioSocketChannel) {
                            ch.pipeline().addLast(StringDecoder())
                            ch.pipeline().addLast(object : ChannelInboundHandlerAdapter() {
                                override fun channelRead(ctx: ChannelHandlerContext, msg: Any) {
                                    log.info("客户端信息 : $msg")
                                }
                            })

                        }
                    })
                //8. 绑定的监听端口
                .bind(8080)
        }
    }
}