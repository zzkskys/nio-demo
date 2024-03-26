package com.example.message

data class RPCResponseMessage(
    val returnValue: Any? = null,
    val e: Exception? = null
) : Message() {
    override fun getMessageType(): Int {
        return RPC_RESPONSE_MESSAGE
    }
}