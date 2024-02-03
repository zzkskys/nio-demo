package com.example.message

data class GroupCreateRequestMsg(
    val group: String,
    val members: MutableSet<String>,
    val owner: String
) : Message() {
    override fun getMessageType(): Int {
        return GROUP_CREATE_REQUEST_MESSAGE
    }

    init {
        members.add(owner)
    }
}