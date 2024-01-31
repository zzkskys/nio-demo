package com.example.netty.c6

import io.netty.channel.nio.NioEventLoopGroup
import org.slf4j.LoggerFactory
import java.util.concurrent.Callable

class TestNettyFuture {
    companion object {

        private val log = LoggerFactory.getLogger(TestNettyFuture::class.java)

        @JvmStatic
        fun main(args: Array<String>) {
            val group = NioEventLoopGroup()
            val eventLoop = group.next()

            val future = eventLoop.submit(Callable {
                log.debug("执行计算")
                Thread.sleep(1_000)
                70
            })

            future.addListener {
                log.debug("接受结果 : ${future.now}")
            }

        }
    }
}