package com.example.server

import com.example.chat.session.InMemorySession
import com.example.chat.session.Session
import io.netty.channel.ChannelHandler.Sharable
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandlerAdapter
import org.slf4j.LoggerFactory

@Sharable
class QuitHandler(
    private val session: Session = InMemorySession()
) : ChannelInboundHandlerAdapter() {

    companion object {
        private val log = LoggerFactory.getLogger(QuitHandler::class.java)
    }

    override fun channelInactive(ctx: ChannelHandlerContext) {
        val channel = ctx.channel()
        session.unbind(channel)
        log.debug("{} 正常断开", channel)
    }

    override fun exceptionCaught(ctx: ChannelHandlerContext, cause: Throwable) {
        val channel = ctx.channel()
        session.unbind(channel)
        log.debug("{} 异常断开,消息: {}", channel, cause.message)
    }
}