package com.example.server

import com.example.message.GroupJoinRequestMsg
import io.netty.channel.ChannelHandler
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler

@ChannelHandler.Sharable
class GroupJoinHandler : SimpleChannelInboundHandler<GroupJoinRequestMsg>() {
    override fun channelRead0(ctx: ChannelHandlerContext?, msg: GroupJoinRequestMsg?) {
        TODO("Not yet implemented")
    }
}