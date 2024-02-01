package com.example.netty.pack

import io.netty.buffer.ByteBuf
import io.netty.buffer.ByteBufAllocator
import io.netty.channel.embedded.EmbeddedChannel
import io.netty.handler.codec.LengthFieldBasedFrameDecoder
import io.netty.handler.logging.LogLevel
import io.netty.handler.logging.LoggingHandler

fun main() {
    val channel = EmbeddedChannel(
        LengthFieldBasedFrameDecoder(1024,0,4,1,4),
        LoggingHandler(LogLevel.DEBUG)
    )
    val buf = ByteBufAllocator.DEFAULT.buffer()
    send(buf,"Hello, World")
    send(buf,"Hi")
    channel.writeInbound(buf)
}

/**
 * 写入 bytebuf
 */
fun send(buf: ByteBuf, content: String,version:Int = 1) {
    buf.writeInt(content.length)
    buf.writeByte(version)
    buf.writeBytes(content.toByteArray())
}