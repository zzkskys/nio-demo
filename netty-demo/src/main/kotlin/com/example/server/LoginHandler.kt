package com.example.server

import com.example.chat.session.InMemorySession
import com.example.chat.session.Session
import com.example.chat.user.InMemoryUserService
import com.example.chat.user.UserService
import com.example.message.LoginRequestMessage
import com.example.message.LoginResponseMessage
import io.netty.channel.ChannelHandler.Sharable
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler

/**
 * 服务器端登录处理
 */
@Sharable
class LoginHandler(
    private val userService: UserService = InMemoryUserService(),
    private val session: Session = InMemorySession()
) : SimpleChannelInboundHandler<LoginRequestMessage>() {
    override fun channelRead0(ctx: ChannelHandlerContext, msg: LoginRequestMessage) {
        val login = userService.login(username = msg.username, password = msg.password)

        val message = if (login) {
            session.bind(ctx.channel(), msg.username)
            LoginResponseMessage(true)
        } else {
            LoginResponseMessage(false, "用户名或密码错误")
        }
        ctx.writeAndFlush(message)
    }
}