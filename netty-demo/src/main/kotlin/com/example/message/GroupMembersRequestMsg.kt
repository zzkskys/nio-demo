package com.example.message

data class GroupMembersRequestMsg(
    val group: String
) : Message() {
    override fun getMessageType(): Int {
        return GROUP_GET_REQUEST_MESSAGE
    }
}