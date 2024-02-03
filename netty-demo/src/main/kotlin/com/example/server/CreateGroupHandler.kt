package com.example.server

import com.example.chat.group.ChatGroupService
import com.example.message.GroupCreateRequestMsg
import com.example.message.GroupCreatedResponseMsg
import io.netty.channel.ChannelHandler.Sharable
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler

@Sharable
class CreateGroupHandler(
    private val groupService: ChatGroupService
) : SimpleChannelInboundHandler<GroupCreateRequestMsg>() {
    override fun channelRead0(ctx: ChannelHandlerContext, msg: GroupCreateRequestMsg) {
        val group = msg.group
        val members = msg.members

        val chatGroup = groupService.createGroup(group, members)
        if (chatGroup == null) {
            //聊天组创建成功
            ctx.writeAndFlush(GroupCreatedResponseMsg(true, "聊天组创建成功"))

            val channels = groupService.getMemberChannels(group)
            channels.forEach { channel ->
                channel.writeAndFlush(GroupCreatedResponseMsg(true,"您已被拉入 $group 聊天组"))
            }
        } else {
            //聊天组创建失败
            ctx.writeAndFlush(GroupCreatedResponseMsg(false, "聊天组 $group 已存在"))
        }

    }
}