package com.example.protocol

import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

interface MessageSerializer {

    /**
     * 将对象序列化
     * @param obj 要序列化的对象
     * @return 返回序列化的结果
     */
    fun <T> serialize(obj: T): ByteArray


    /**
     * 反序列化结果
     */
    fun <T> deserialize( bytes: ByteArray): T


    enum class Algorithm(
        val type:Int
    ) : MessageSerializer {
        JDK(0) {
            override fun <T> serialize(obj: T): ByteArray {
                //将正文序列化
                val bos = ByteArrayOutputStream()
                val oos = ObjectOutputStream(bos)
                oos.writeObject(obj)
                return bos.toByteArray()
            }

            override fun <T> deserialize(bytes: ByteArray): T {
                val ois = ObjectInputStream(ByteArrayInputStream(bytes))
                return ois.readObject() as T
            }

        }
    }
}