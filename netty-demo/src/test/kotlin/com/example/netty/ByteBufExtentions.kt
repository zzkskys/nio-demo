package com.example.netty

import io.netty.buffer.ByteBuf
import io.netty.buffer.ByteBufUtil.appendPrettyHexDump
import io.netty.util.internal.StringUtil.NEWLINE

fun ByteBuf.log() {
//    val length = this.readableBytes()
//    val rows = length / 16 + (if (length % 15 == 0) 0 else 1) + 4
    val len = "read index : ${this.readerIndex()} write index : ${this.writerIndex()} capacity : ${this.capacity()}"
    val sb = StringBuilder()
        .append(len)
        .append(NEWLINE)
    appendPrettyHexDump(sb,this)
    println(sb.toString())
}