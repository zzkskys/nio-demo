package com.example.chat.group

import com.example.chat.session.InMemorySession
import com.example.chat.session.Session
import io.netty.channel.Channel
import java.util.concurrent.ConcurrentHashMap

interface ChatGroupService {

    /**
     * 创建一个聊天组
     * @param name 组的别名
     * @param members 创建时要加入的成员
     * @return 若聊天组存在,返回 null ; 若聊天组不存在则返回创建的的聊天组
     */
    fun createGroup(name: String, members: Set<String>): ChatGroup?

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
     * @param group 组名称
     */
    fun getMemberChannels(group: String): List<Channel>
}

class InMemoryGroupService(
    private val session: Session = InMemorySession()
) : ChatGroupService {

    private val groupMap = ConcurrentHashMap<String, ChatGroup>()
    override fun createGroup(name: String, members: Set<String>): ChatGroup? {
        val chatGroup = groupMap[name]
        if (chatGroup != null) return chatGroup

        val newGroup = ChatGroup(name, members)
        groupMap[name] = newGroup
        return null
    }

    override fun joinGroup(name: String, member: String): ChatGroup {
        TODO("Not yet implemented")
    }

    override fun removeMember(name: String, member: String): ChatGroup {
        TODO("Not yet implemented")
    }

    override fun getMembers(): Set<String> {
        TODO("Not yet implemented")
    }

    override fun getMemberChannels(group: String): List<Channel> {
        val members = groupMap[group]?.members ?: emptySet()

        return members.mapNotNull { session.getChannel(it) }
    }
}