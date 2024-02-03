package com.example.message

data class SendRequestMessage(
    var from: String,
    var to: String,
    var content: String
) : Message() {
    override fun getMessageType(): Int {
        return CHAT_REQUEST_MESSAGE
    }
}