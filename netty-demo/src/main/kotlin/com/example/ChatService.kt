package com.example

import com.example.chat.group.InMemoryGroupService
import com.example.chat.session.InMemorySession
import com.example.chat.user.InMemoryUserService
import com.example.protocol.MessageCodecSharable
import com.example.protocol.MessageProcotolDecoder
import com.example.server.*
import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.ChannelInitializer
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioServerSocketChannel
import io.netty.handler.logging.LogLevel
import io.netty.handler.logging.LoggingHandler


fun main() {
    val boss = NioEventLoopGroup()
    val worker = NioEventLoopGroup()

    val logHandler = LoggingHandler(LogLevel.DEBUG)
    val messageCodecSharable = MessageCodecSharable()
    val userService = InMemoryUserService()
    val session = InMemorySession()
    val groupService = InMemoryGroupService(session = session)

    try {
        val bootstrap = ServerBootstrap()
        bootstrap.channel(NioServerSocketChannel::class.java)
            .group(boss, worker)
            .childHandler(object : ChannelInitializer<SocketChannel>() {
                override fun initChannel(ch: SocketChannel) {
                    ch.pipeline()
                        .addLast(MessageProcotolDecoder())
                        .addLast(logHandler)
                        .addLast(messageCodecSharable)
                        .addLast(LoginHandler(userService = userService, session = session))
                        .addLast(ChatHandler(session = session))
                        .addLast(CreateGroupHandler(groupService = groupService))
                        .addLast(GroupChatHandler(groupService = groupService))
                        .addLast(QuitHandler(session = session))
                }
            })

        val channelFuture = bootstrap.bind(8080).sync()
        channelFuture.channel().closeFuture().sync()

    } finally {
        boss.shutdownGracefully()
        worker.shutdownGracefully()
    }
}