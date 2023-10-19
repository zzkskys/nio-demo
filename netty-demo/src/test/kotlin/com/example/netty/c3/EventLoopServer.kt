package com.example.netty.c3

import io.netty.bootstrap.ServerBootstrap
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandlerAdapter
import io.netty.channel.ChannelInitializer
import io.netty.channel.DefaultEventLoopGroup
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioServerSocketChannel
import io.netty.channel.socket.nio.NioSocketChannel
import org.slf4j.LoggerFactory
import java.nio.charset.Charset

/**
 *
 * @since : 2023/10/19
 * @author zzk
 */
class EventLoopServer {
    companion object {

        val log = LoggerFactory.getLogger(EventLoopServer::class.java)

        @JvmStatic
        fun main(args: Array<String>) {
            val group = DefaultEventLoopGroup()

            ServerBootstrap()
                /*
                    第一个为 boss,只负责 accept 事件。
                    第二个为 worker,负责后续的读写操作。
                 */
                .group(NioEventLoopGroup(), NioEventLoopGroup(2))
                .channel(NioServerSocketChannel::class.java)
                .childHandler(object : ChannelInitializer<NioSocketChannel>() {
                    override fun initChannel(ch: NioSocketChannel) {
                        ch
                            .pipeline()
                            .addLast("handler1", object : ChannelInboundHandlerAdapter() {
                                override fun channelRead(ctx: ChannelHandlerContext, msg: Any) {
                                    msg as ByteBuf
                                    log.debug(msg.toString(Charset.defaultCharset()))
                                    ctx.fireChannelRead(msg) //将内容转交给下一个 handler 处理
                                }
                            })
                            .addLast(group, "handler2", object : ChannelInboundHandlerAdapter() {
                                override fun channelRead(ctx: ChannelHandlerContext, msg: Any) {
                                    msg as ByteBuf
                                    log.debug(msg.toString(Charset.defaultCharset()))
                                }
                            })

                    }
                })
                .bind(8080)
        }
    }
}