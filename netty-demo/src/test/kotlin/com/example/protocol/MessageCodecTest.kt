package com.example.protocol

import com.example.message.LoginRequestMessage
import io.netty.buffer.ByteBufAllocator
import io.netty.channel.embedded.EmbeddedChannel
import io.netty.handler.logging.LogLevel
import io.netty.handler.logging.LoggingHandler
import org.junit.jupiter.api.Test

class MessageCodecTest {

    @Test
    fun test() {
        val channel = EmbeddedChannel(
            MessageProcotolDecoder(),
            LoggingHandler(LogLevel.DEBUG),
            MessageCodec()
        )

        //encode
        val message = LoginRequestMessage("zhangsan", "123")
        channel.writeOutbound(message)

        //decode
        val buf = ByteBufAllocator.DEFAULT.buffer()
        MessageCodec().encode(null, message, buf)
        channel.writeInbound(buf)
    }
}