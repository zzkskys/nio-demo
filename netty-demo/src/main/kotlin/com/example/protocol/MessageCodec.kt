package com.example.protocol

import com.example.message.Message
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.ByteToMessageCodec
import org.slf4j.LoggerFactory

class MessageCodec : ByteToMessageCodec<Message>() {

    companion object {
        private val log = LoggerFactory.getLogger(MessageCodec::class.java)
    }

    public override fun encode(ctx: ChannelHandlerContext?, msg: Message, out: ByteBuf) {
        //字节魔数:占用 4 字节
        out.writeBytes(byteArrayOf(1, 2, 3, 4))

        //版本 ： 占用 1 字节
        out.writeByte(1)

        //序列化方式 : 0 表示 jdk , 1 表示 json   占用 1 字节
        //目前先用 0 写死
        out.writeByte(0)

        //字节的指令类型 : 占用 1 字节
        out.writeByte(msg.getMessageType())

        //请求序号 : 请求序号 ，占用 4 字节
        out.writeInt(msg.getSequenceId())
        //无意义的字节 : 用于对其填充使用
        out.writeByte(0xff)

//        //将正文序列化
        val bytes = MessageSerializer.Algorithm.JDK.serialize(msg)

        // 长度 : 占用 4 字节
        // 正文
        out.writeInt(bytes.size)
        out.writeBytes(bytes)
    }

    override fun decode(ctx: ChannelHandlerContext, `in`: ByteBuf, out: MutableList<Any>) {
        //读取 4 字节
        val magicNum = `in`.readInt()
        val v = `in`.readByte()
        val serializerType = `in`.readByte()
        val messageType = `in`.readByte()
        val sequenceId = `in`.readInt()
        `in`.readByte()
        val length = `in`.readInt()

        val bytes = ByteArray(length)
        `in`.readBytes(bytes, 0, length)

        val message: Any = MessageSerializer.Algorithm.JDK.deserialize(bytes)

        out.add(message)
        log.debug("magicNum : $magicNum, version : ${v.toInt()} , serializerType : ${serializerType.toInt()}, messageType : ${messageType.toInt()}, sequenceId : $sequenceId , length : $length")
        log.debug("message : {}", message)

    }


}