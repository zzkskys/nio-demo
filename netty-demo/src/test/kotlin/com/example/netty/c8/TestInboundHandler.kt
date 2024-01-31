package com.example.netty.c8

import io.netty.bootstrap.ServerBootstrap
import io.netty.buffer.ByteBuf
import io.netty.channel.*
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioServerSocketChannel
import io.netty.channel.socket.nio.NioSocketChannel
import org.slf4j.LoggerFactory
import java.nio.charset.Charset

class TestInboundHandler {
    companion object {

        private val log = LoggerFactory.getLogger(TestOutboundHandler::class.java)

        @JvmStatic
        fun main(args: Array<String>) {
            ServerBootstrap()
                .group(NioEventLoopGroup())
                .channel(NioServerSocketChannel::class.java)
                .childHandler(object : ChannelInitializer<NioSocketChannel>() {
                    override fun initChannel(ch: NioSocketChannel) {
                        val pipeline = ch.pipeline()

                        pipeline.addLast("inbound-h1", object : ChannelInboundHandlerAdapter() {
                            override fun channelRead(ctx: ChannelHandlerContext, msg: Any) {
                                log.debug("inbound-h1")
                                msg as ByteBuf
                                val name = msg.toString(Charset.defaultCharset())
                                super.channelRead(ctx, name)
                            }
                        })
                        pipeline.addLast("inbound-h2", object : ChannelInboundHandlerAdapter() {
                            override fun channelRead(ctx: ChannelHandlerContext, msg: Any) {
                                msg as String
                                val student = Student(msg)
                                log.debug("inbound-h2")
                                super.channelRead(ctx, student)
                            }
                        })
                        pipeline.addLast("inbound-h3", object : ChannelInboundHandlerAdapter() {
                            override fun channelRead(ctx: ChannelHandlerContext, msg: Any) {
                                msg as Student
                                log.debug("student name : ${msg.name}")
                                //只是为了出发出站
                                ch.writeAndFlush(ctx.alloc().buffer().writeBytes("server...".toByteArray()))
                                super.channelRead(ctx, msg)
                            }
                        })
                    }
                })
                .bind(8080)
        }
    }
}

