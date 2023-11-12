package com.example.netty.c5

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

class NettyClient {
    companion object {

        private val log = LoggerFactory.getLogger(NettyServer::class.java)

        @JvmStatic
        fun main(args: Array<String>) {
            val group = NioEventLoopGroup()
            val channel = Bootstrap()
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
                .sync()
                .channel()


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
            val cf = channel.closeFuture()
            cf.addListener {
                log.debug("关闭了客户端线程，要进行如下的操作.........")
                group.shutdownGracefully() // 优雅关闭线程
            }
        }
    }
}