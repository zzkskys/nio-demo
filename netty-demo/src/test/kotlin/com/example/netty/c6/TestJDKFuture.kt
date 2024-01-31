package com.example.netty.c6

import org.slf4j.LoggerFactory
import java.util.concurrent.Callable
import java.util.concurrent.Executors

/**
 * 测试 JDKFuture
 */
class TestJDKFuture {
    companion object {

        private val log = LoggerFactory.getLogger(TestJDKFuture::class.java)

        @JvmStatic
        fun main(args: Array<String>) {
            val pool = Executors.newFixedThreadPool(2)
            val future = pool.submit(Callable {
                log.debug("开始计算")
                Thread.sleep(1_000)
                50
            })


            log.debug("等待结果")
            log.debug("结果是 : ${future.get()}")
        }
    }
}
