package com.example.message

data class GroupChatResponseMessage(
    val from: String,
    val group: String,
    val content: String
) : Message() {
    override fun getMessageType(): Int {
        return CHAT_RESPONSE_MESSAGE
    }
}