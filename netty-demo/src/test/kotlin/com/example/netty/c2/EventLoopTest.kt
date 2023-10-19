package com.example.netty.c2

import io.netty.channel.nio.NioEventLoopGroup
import java.util.concurrent.TimeUnit

/**
 *
 * @since : 2023/10/19
 * @author zzk
 */
fun main() {


    /*
        NioEventLoopGroup 功能较为强大，可以处理 IO、事件、普通任务和定时任务
        DefaultEventLoopGroup 只能处理 普通任务、定时任务

        nThreads: 指定线程数，若不指定则默认是 系统的核心数*2
     */
    val group = NioEventLoopGroup(2)

    //普通任务
    group.next().submit {
        Thread.sleep(1000)
        println("ok")
    }

    //定时任务
    group.next().next().scheduleAtFixedRate({
            println("ok 定时任务......................")
        }, 1, 10, TimeUnit.SECONDS
    )
}

