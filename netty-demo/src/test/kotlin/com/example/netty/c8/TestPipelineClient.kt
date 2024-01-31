package com.example.netty.c8

import io.netty.bootstrap.Bootstrap
import io.netty.channel.ChannelInitializer
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioSocketChannel
import io.netty.handler.codec.string.StringEncoder
import io.netty.handler.logging.LogLevel
import io.netty.handler.logging.LoggingHandler
import org.slf4j.LoggerFactory
import java.net.InetSocketAddress
import java.util.*

class TestPipelineClient {
    companion object {

        private val log = LoggerFactory.getLogger(TestPipelineClient::class.java)

        @JvmStatic
        fun main(args: Array<String>) {
            val group = NioEventLoopGroup()
            val connect = Bootstrap()
                .group(group)
                .channel(NioSocketChannel::class.java)
                .handler(object : ChannelInitializer<NioSocketChannel>() {
                    override fun initChannel(ch: NioSocketChannel) {
                        ch.pipeline()
                            .addLast(LoggingHandler(LogLevel.DEBUG))
                            .addLast(StringEncoder())
                    }
                })
                .connect(InetSocketAddress("localhost", 8080))
            val channel = connect
                .sync()
                .channel()

            //连接建立成功回调处理
            connect.addListener {
                log.debug("连接建立成功........")
            }

            object : Thread("input"){
                override fun run() {
                    val scanner = Scanner(System.`in`)
                    while (true) {
                        val str = scanner.nextLine()
                        if ("q" == str) {
                            channel.close()
                            break
                        }
                        channel.writeAndFlush(str)
                    }
                }
            }.start()
            //连接关闭回调处理
            val cf = channel.closeFuture()
            cf.addListener {
                log.debug("关闭了客户端线程，要进行如下的操作.........")
                group.shutdownGracefully() // 优雅关闭线程
            }
        }
    }
}