package com.example.netty.c6

import io.netty.channel.nio.NioEventLoopGroup
import io.netty.util.concurrent.DefaultPromise
import org.slf4j.LoggerFactory

class TestNettyPromise {
    companion object {

        private val log = LoggerFactory.getLogger(TestNettyPromise::class.java)

        @JvmStatic
        fun main(args: Array<String>) {
            val eventLoop = NioEventLoopGroup().next()

            val promise = DefaultPromise<Int>(eventLoop)

            Thread {
                log.debug("开始计算...")
                Thread.sleep(1_000)
                promise.setSuccess(80)
            }.start()

            log.debug("等待结果...")
            log.debug("结果是: ${promise.get()}")
        }
    }
}