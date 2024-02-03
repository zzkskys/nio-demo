package com.example.protocol

import com.example.message.Message
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandler.Sharable
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.MessageToMessageCodec
import org.slf4j.LoggerFactory
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

/**
 * 必须和 [MessageProcotolDecoder] 一起使用
 */
@Sharable
class MessageCodecSharable : MessageToMessageCodec<ByteBuf, Message>() {

    companion object {
        private val log = LoggerFactory.getLogger(MessageCodecSharable::class.java)
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

        //todo : 需要分析 type
        val ois = ObjectInputStream(ByteArrayInputStream(bytes))
        val message: Message = ois.readObject() as Message
        out.add(message)
        log.debug("magicNum : $magicNum, version : ${v.toInt()} , serializerType : ${serializerType.toInt()}, messageType : ${messageType.toInt()}, sequenceId : $sequenceId , length : $length")
        log.debug("message : {}", message)

    }

    override fun encode(ctx: ChannelHandlerContext, msg: Message, outList: MutableList<Any>) {
        val out = ctx.alloc().buffer()

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

        //将正文序列化
        val bos = ByteArrayOutputStream()
        val oos = ObjectOutputStream(bos)
        oos.writeObject(msg)
        val bytes = bos.toByteArray()

        // 长度 : 占用 4 字节
        // 正文
        out.writeInt(bytes.size)
        out.writeBytes(bytes)

        outList.add(out)
    }


}