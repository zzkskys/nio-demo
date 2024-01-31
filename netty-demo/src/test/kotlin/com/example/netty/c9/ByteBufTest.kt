package com.example.netty.c9

import com.example.netty.log
import io.netty.buffer.ByteBufAllocator

fun main() {
//    val buf = ByteBufAllocator.DEFAULT.buffer()
//    buf.writeBytes(byteArrayOf(1,2,3,4))
//    buf.log()
//
//    println(buf.readByte())
//    println(buf.readByte())
//    println(buf.readByte())
//    println(buf.readByte())

    val buf = ByteBufAllocator.DEFAULT.buffer()
    buf.writeInt(5)
    buf.writeInt(6)
    buf.log()

    buf.markReaderIndex()
    println(buf.readInt())
    buf.log()
}

