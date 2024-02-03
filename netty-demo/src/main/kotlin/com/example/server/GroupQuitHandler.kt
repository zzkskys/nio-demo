package com.example.server

import com.example.message.GroupQuitRequestMsg
import io.netty.channel.ChannelHandler.Sharable
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler

@Sharable
class GroupQuitHandler : SimpleChannelInboundHandler<GroupQuitRequestMsg>() {
    override fun channelRead0(ctx: ChannelHandlerContext?, msg: GroupQuitRequestMsg?) {
        TODO("Not yet implemented")
    }
}