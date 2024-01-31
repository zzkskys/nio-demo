package com.example.netty.c8

import io.netty.buffer.ByteBuf
import io.netty.buffer.ByteBufAllocator
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandlerAdapter
import io.netty.channel.ChannelOutboundHandlerAdapter
import io.netty.channel.ChannelPromise
import io.netty.channel.embedded.EmbeddedChannel
import org.slf4j.LoggerFactory

class TestEmbeddedChannel {
    companion object {

        private val log = LoggerFactory.getLogger(TestEmbeddedChannel::class.java)

        @JvmStatic
        fun main(args: Array<String>) {
            val h1 = object : ChannelInboundHandlerAdapter() {
                override fun channelRead(ctx: ChannelHandlerContext?, msg: Any?) {
                    log.debug("h1")
                    super.channelRead(ctx, msg)
                }
            }
            val h2 = object : ChannelInboundHandlerAdapter() {
                override fun channelRead(ctx: ChannelHandlerContext?, msg: Any?) {
                    log.debug("h2")
                    super.channelRead(ctx, msg)
                }
            }
            val h3 = object : ChannelOutboundHandlerAdapter() {
                override fun write(ctx: ChannelHandlerContext?, msg: Any?, promise: ChannelPromise?) {
                    log.debug("h3")
                    super.write(ctx, msg, promise)
                }
            }
            val h4 = object : ChannelOutboundHandlerAdapter() {
                override fun write(ctx: ChannelHandlerContext?, msg: Any?, promise: ChannelPromise?) {
                    log.debug("h4")
                    super.write(ctx, msg, promise)
                }
            }
            val msg = ByteBufAllocator.DEFAULT.buffer().writeBytes("hello".toByteArray())
            val channel = EmbeddedChannel(h1,h2,h3,h4)
            channel.writeInbound(msg)
            channel.writeOutbound(msg)
        }
    }
}