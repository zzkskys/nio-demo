package com.example.message

data class GroupCreatedResponseMsg(
    val susses: Boolean,
    val content: String
) : Message() {
    override fun getMessageType(): Int {
        return GROUP_CREATE_RESPONSE_MESSAGE
    }
}