package com.example.client

import com.example.message.LoginRequestMessage
import com.example.message.LoginResponseMessage
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandlerAdapter
import org.slf4j.LoggerFactory
import java.util.*
import java.util.concurrent.CountDownLatch
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.concurrent.thread

/**
 * 当客户端连接服务器端时，登录处理
 */
class ClientLoginHandler : ChannelInboundHandlerAdapter() {

    private val waitForLogin = CountDownLatch(1)

    private val loginSuccess = AtomicBoolean(false)

    companion object {
        private val log = LoggerFactory.getLogger(ClientLoginHandler::class.java)
    }

    override fun channelRead(ctx: ChannelHandlerContext, msg: Any) {
        log.debug("message: {}", msg)
        if (msg is LoginResponseMessage) {
            if (msg.success) {
                loginSuccess.set(true)
            }
            waitForLogin.countDown()
        }
    }

    override fun channelActive(ctx: ChannelHandlerContext) {
        thread(name = "login") {
            val sc = Scanner(System.`in`)
            println("请输入用户名: ")
            val username = sc.nextLine()
            println("请输入密码: ")
            val password = sc.nextLine()

            val message = LoginRequestMessage(username = username, password = password)
            ctx.writeAndFlush(message)

            waitForLogin.await()
            if (!loginSuccess.get()) {
                //登录失败处理
                ctx.channel().close()
                return@thread
            }
            CurrentUserHolder.username = username

            while (true) {
                println(CommandDecoder.menu)

                val cmd = sc.nextLine()
                if ("quit" == cmd) {
                    ctx.channel().close()
                    break
                } else {
                    val d = CommandDecoder(cmd)
                    ctx.writeAndFlush(d.message)
                }
            }

        }.start()
    }
}