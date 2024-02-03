package com.example.chat.session

import io.netty.channel.Channel
import java.util.concurrent.ConcurrentHashMap

interface Session {

    /**
     * 将用户与 channel 绑定
     * @param channel 要绑定的 channel
     * @param username 要绑定的用户
     */
    fun bind(channel: Channel, username: String)


    /**
     * 解绑会话
     */
    fun unbind(channel: Channel)


    /**
     * 根据用户名查找 channel
     */
    fun getChannel(username: String): Channel?
}

class InMemorySession : Session {

    private val usernameChannelMap = ConcurrentHashMap<String, Channel>()

    private val channelUsernameMap = ConcurrentHashMap<Channel, String>()

    override fun bind(channel: Channel, username: String) {
        usernameChannelMap[username] = channel
        channelUsernameMap[channel] = username
    }

    override fun unbind(channel: Channel) {
        val username = channelUsernameMap.remove(channel)
        if (username != null) {
            usernameChannelMap.remove(username)
        }
    }

    override fun getChannel(username: String): Channel? {
        return usernameChannelMap[username]
    }
}