package com.example.chat.group

import io.netty.channel.Channel

interface ChatGroupService {

    /**
     * 创建一个聊天组
     * @param name 组的别名
     * @param members 创建时要加入的成员
     * @return 返回创建的组
     */
    fun createGroup(name: String, members: Set<String>): ChatGroup

    /**
     * 加入聊天组
     * @param name 组名
     * @param member 成员名
     * @return 返回要加入的组
     */
    fun joinGroup(name: String, member: String): ChatGroup

    /**
     * 移除成员
     * @param name 要移除的组名
     * @param member 要移除的成员
     */
    fun removeMember(name: String, member: String): ChatGroup

    /**
     * 获取组成员
     */
    fun getMembers(): Set<String>

    /**
     * 获取组员的 channel
     * @param name 组名称
     */
    fun getMemberChannels(name: String): List<Channel>
}