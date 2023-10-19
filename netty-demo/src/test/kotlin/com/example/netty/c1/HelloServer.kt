package com.example.netty.c1

import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandlerAdapter
import io.netty.channel.ChannelInitializer
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioServerSocketChannel
import io.netty.channel.socket.nio.NioSocketChannel
import io.netty.handler.codec.string.StringDecoder

/**
 * 最简单的 NIO 服务端
 * @since : 2023/10/19
 * @author zzk
 */

fun main() {
    /*
        1. 服务器端启动器。
        负责组装 netty 组件，启动服务器
     */
    ServerBootstrap()
        /*
            2. 包含 Selector 与 线程
         */
        .group(NioEventLoopGroup())
        /*
            3. 选择服务器的 SocketChannel 实现
         */
        .channel(NioServerSocketChannel::class.java)
        /*
            4. 处理 child 相关逻辑。
            boss 负责建立连接， worker(child) 负责处理读写。
         */
        .childHandler(
            /*
                5. 代表与客户端进行数据读写的通道初始化工作,负责添加其他的 handler
             */
            object : ChannelInitializer<NioSocketChannel>() {
                override fun initChannel(ch: NioSocketChannel) {
                    /*
                        6.String 解码器。
                        将 byte 转换为字符串
                     */
                    ch.pipeline().addLast(StringDecoder())
                    /*
                        7. 读事件
                        目前的逻辑是打印客户端
                     */
                    ch.pipeline().addLast(object : ChannelInboundHandlerAdapter() {
                        override fun channelRead(ctx: ChannelHandlerContext, msg: Any) {
                            println(msg)
                        }
                    })

                }
            })
        //8. 绑定的监听端口
        .bind(8080)
}
