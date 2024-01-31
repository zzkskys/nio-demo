package com.example.netty.c7

import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.*
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioServerSocketChannel
import io.netty.channel.socket.nio.NioSocketChannel
import org.slf4j.LoggerFactory

class TestPipelineService {
    companion object {

        private val log = LoggerFactory.getLogger(TestPipelineService::class.java)

        @JvmStatic
        fun main(args: Array<String>) {
            ServerBootstrap()
                .group(NioEventLoopGroup())
                .channel(NioServerSocketChannel::class.java)
                .childHandler(object : ChannelInitializer<NioSocketChannel>() {
                    override fun initChannel(ch: NioSocketChannel) {
                        //注 : 当 pipeline 初始化时，会默认存在两个 ChannelHandler,分别是 : head 与 tail
                        val pipeline = ch.pipeline()

                        pipeline.addLast("inbound-h1", object : ChannelInboundHandlerAdapter() {
                            override fun channelRead(ctx: ChannelHandlerContext, msg: Any) {
                                log.debug("inbound-h1")
                                super.channelRead(ctx, msg)
                            }
                        })
                        pipeline.addLast("inbound-h2", object : ChannelInboundHandlerAdapter() {
                            override fun channelRead(ctx: ChannelHandlerContext, msg: Any) {
                                log.debug("inbound-h2")
                                super.channelRead(ctx, msg)
                            }
                        })
                        pipeline.addLast("inbound-h3", object : ChannelInboundHandlerAdapter() {
                            override fun channelRead(ctx: ChannelHandlerContext, msg: Any) {
                                log.debug("inbound-h3")
                                //只是为了出发出站
                                ch.writeAndFlush(ctx.alloc().buffer().writeBytes("server...".toByteArray()))
                                super.channelRead(ctx, msg)
                            }
                        })

                        pipeline.addLast("outbound-h4", object : ChannelOutboundHandlerAdapter() {
                            override fun write(ctx: ChannelHandlerContext?, msg: Any?, promise: ChannelPromise?) {
                                log.debug("outbound-h4")
                                super.write(ctx, msg, promise)
                            }
                        })
                        pipeline.addLast("outbound-h5", object : ChannelOutboundHandlerAdapter() {
                            override fun write(ctx: ChannelHandlerContext?, msg: Any?, promise: ChannelPromise?) {
                                log.debug("outbound-h5")
                                super.write(ctx, msg, promise)
                            }
                        })
                        pipeline.addLast("outbound-h6", object : ChannelOutboundHandlerAdapter() {
                            override fun write(ctx: ChannelHandlerContext?, msg: Any?, promise: ChannelPromise?) {
                                log.debug("outbound-h6")
                                super.write(ctx, msg, promise)
                            }
                        })

                    }
                })
                .bind(8080)
        }
    }
}