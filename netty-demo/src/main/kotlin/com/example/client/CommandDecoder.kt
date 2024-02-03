package com.example.client

import com.example.message.*

class CommandDecoder(
    command: String
) {

    var message: Message


    companion object {
        val menu = """
                ===========操作菜单=================
                send [username] [content]
                gsend [group name] [content]
                gcreate [group name] [m1,m2,m3...]
                gmembers [group name]
                gjoin [group name]
                gquit [group name]
                quit
                ===================================
            """.trimIndent()
    }

    init {
        val split = command.split(" ")
        val username = CurrentUserHolder.username
        this.message = when (split.first()) {
            "send" -> SendRequestMessage(from = username, to = split[1], content = split[2])
            "gsend" -> GroupSendRequestMsg(from = username, group = split[1], content = split[2])
            "gcreate" -> GroupCreateRequestMsg(
                group = split[1],
                members = split[2].split(",").toMutableSet(),
                owner = username
            )
            "gmembers" -> GroupMembersRequestMsg(group = split[1])
            "gjoin" -> GroupJoinRequestMsg(group = split[1])
            "gquit" -> GroupQuitRequestMsg(group = split[1])
            "quit" -> TODO()
            else -> TODO()
        }
    }
}