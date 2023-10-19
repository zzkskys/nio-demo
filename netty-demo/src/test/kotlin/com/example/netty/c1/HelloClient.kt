package com.example.netty.c1

import io.netty.bootstrap.Bootstrap
import io.netty.channel.ChannelInitializer
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioSocketChannel
import io.netty.handler.codec.string.StringEncoder
import java.net.InetSocketAddress

/**
 *  最简单的 NIO 客户端
 * @since : 2023/10/19
 * @author zzk
 */

fun main() {
    //1. 启动类
    Bootstrap()
        //2. 添加 EventLoop
        .group(NioEventLoopGroup())
        //3. 选择客户端 channel 实现
        .channel(NioSocketChannel::class.java)
        //4. 添加处理器
        .handler(object : ChannelInitializer<NioSocketChannel>() {
            override fun initChannel(ch: NioSocketChannel) {
                //5. 添加字符串编码
                ch.pipeline().addLast(StringEncoder())
            }
        })
        .connect(InetSocketAddress("localhost", 8080))
        .sync()
        .channel()
        //向服务器发送数据
        .writeAndFlush("hello world")
}
