package com.example.netty.c9

import com.example.netty.log
import io.netty.buffer.ByteBufAllocator

fun main() {
    val buf1 = ByteBufAllocator.DEFAULT.buffer()
    buf1.writeBytes(byteArrayOf(1,2,3,4,5))

    val buf2 = ByteBufAllocator.DEFAULT.buffer()
    buf2.writeBytes(byteArrayOf(6,7,8,9,10))


    val buf3 = ByteBufAllocator.DEFAULT.compositeBuffer()
    buf3.addComponents(true,buf1,buf2)

    buf3.log()
}