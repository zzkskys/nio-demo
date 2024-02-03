package com.example.server

import com.example.chat.session.InMemorySession
import com.example.chat.session.Session
import com.example.message.ChatResponseMsg
import com.example.message.SendRequestMessage
import io.netty.channel.ChannelHandler.Sharable
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler

/**
 * 消息发送处理器
 */
@Sharable
class ChatHandler(
    private val session: Session = InMemorySession()
) : SimpleChannelInboundHandler<SendRequestMessage>() {
    override fun channelRead0(ctx: ChannelHandlerContext, msg: SendRequestMessage) {
        val to = msg.to
        val channel = session.getChannel(msg.to)

        if (channel != null) {
            channel.writeAndFlush(ChatResponseMsg(success = true, content = msg.content, from = to))
        } else {
            ctx.writeAndFlush(ChatResponseMsg(success = false, content = "目标用户不存在"))
        }
    }
}