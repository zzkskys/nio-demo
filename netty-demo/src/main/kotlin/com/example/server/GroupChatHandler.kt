package com.example.server

import com.example.chat.group.ChatGroupService
import com.example.chat.group.InMemoryGroupService
import com.example.message.GroupChatResponseMessage
import com.example.message.GroupSendRequestMsg
import io.netty.channel.ChannelHandler.Sharable
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler

@Sharable
class GroupChatHandler(
    private val groupService: ChatGroupService = InMemoryGroupService()
) : SimpleChannelInboundHandler<GroupSendRequestMsg>() {
    override fun channelRead0(ctx: ChannelHandlerContext, msg: GroupSendRequestMsg) {
        val channels = groupService.getMemberChannels(msg.group)
        channels.forEach { channel ->
            channel.writeAndFlush(GroupChatResponseMessage(from = msg.from, group = msg.group, content = msg.content))
        }
    }
}