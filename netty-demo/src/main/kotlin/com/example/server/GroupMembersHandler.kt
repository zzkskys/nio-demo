package com.example.server

import com.example.message.GroupMembersRequestMsg
import io.netty.channel.ChannelHandler.Sharable
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler

@Sharable
class GroupMembersHandler : SimpleChannelInboundHandler<GroupMembersRequestMsg>() {
    override fun channelRead0(ctx: ChannelHandlerContext?, msg: GroupMembersRequestMsg?) {
        TODO("Not yet implemented")
    }
}