package com.example.message

data class ChatResponseMsg(
    val success: Boolean = false,
    val content: String = "",
    val from: String? = null
) : Message() {
    override fun getMessageType(): Int {
        return CHAT_RESPONSE_MESSAGE
    }
}