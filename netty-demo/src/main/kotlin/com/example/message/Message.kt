package com.example.message

import java.io.Serializable

abstract class Message : Serializable {

    /**
     * 获取消息类型
     */
    abstract fun getMessageType():Int

    /**
     * 获取序号
     */
    open fun getSequenceId():Int = 0

    companion object{

        /**
         * 登录
         */
        const val LOGIN_REQUEST_MESSAGE = 0

        /**
         * 登录响应消息
         */
        const val LOGIN_RESPONSE_MESSAGE = 1

        /**
         * 聊天
         */
        const val CHAT_REQUEST_MESSAGE = 2

        /**
         * 聊天回应
         */
        const val CHAT_RESPONSE_MESSAGE = 3

    }
}