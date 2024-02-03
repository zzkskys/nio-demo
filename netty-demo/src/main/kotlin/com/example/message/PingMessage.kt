package com.example.message

data class PingMessage(
    val ping: Boolean = true
) : Message() {
    override fun getMessageType(): Int {
        return PING
    }
}