package com.example.netty.echo

import io.netty.bootstrap.Bootstrap
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandlerAdapter
import io.netty.channel.ChannelInitializer
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioSocketChannel
import io.netty.handler.codec.string.StringDecoder
import io.netty.handler.codec.string.StringEncoder
import java.net.InetSocketAddress
import java.util.*

fun main() {
    val group = NioEventLoopGroup()
    val connect = Bootstrap()
        .group(group)
        .channel(NioSocketChannel::class.java)
        .handler(object : ChannelInitializer<NioSocketChannel>() {
            override fun initChannel(ch: NioSocketChannel) {
                ch.pipeline()
                    .addLast(StringEncoder())
                    .addLast(StringDecoder())
                    .addLast(ServerEchoHandler())
            }
        })
        .connect(InetSocketAddress("localhost", 8080))
    val channel = connect
        .sync()
        .channel()

    //连接建立成功回调处理
    connect.addListener {
        println("连接建立成功........")
    }

    object : Thread("input") {
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
        println("关闭了客户端线程，要进行如下的操作.........")
        group.shutdownGracefully() // 优雅关闭线程
    }

}

class ServerEchoHandler : ChannelInboundHandlerAdapter() {
    override fun channelRead(ctx: ChannelHandlerContext, msg: Any) {
        if (msg is String) {
            println("服务器回应 ： $msg")
        }
        super.channelRead(ctx, msg)
    }
}
