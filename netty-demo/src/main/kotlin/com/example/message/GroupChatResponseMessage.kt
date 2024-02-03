package com.example.message

data class GroupChatResponseMessage(
    val from: String,
    val group: String,
    val content: String
) : Message() {
    override fun getMessageType(): Int {
        return GROUP_CHAT_RESPONSE_MESSAGE
    }
}