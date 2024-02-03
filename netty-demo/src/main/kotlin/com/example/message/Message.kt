package com.example.message

import java.io.Serializable

abstract class Message : Serializable {

    /**
     * 获取消息类型
     */
    abstract fun getMessageType(): Int

    /**
     * 获取序号
     */
    open fun getSequenceId(): Int = 0


    companion object {


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

        /**
         * 群聊
         */
        const val GROUP_CHAT_REQUEST_MESSAGE = 4

        /**
         * 群聊响应
         */
        const val GROUP_CHAT_RESPONSE_MESSAGE = 5

        /**
         * 群聊请求
         */
        const val GROUP_CREATE_REQUEST_MESSAGE = 6

        /**
         * 群聊创建响应
         */
        const val GROUP_CREATE_RESPONSE_MESSAGE = 7

        /**
         * 获取组的成员
         */
        const val GROUP_GET_REQUEST_MESSAGE = 8

        /**
         * 加入组
         */
        const val GROUP_JOIN_REQUEST_MESSAGE = 10


        /**
         * 退出组
         */
        const val GROUP_QUIT_REQUEST_MESSAGE = 12

        /**
         * ping
         */
        const val PING = 13

    }
}