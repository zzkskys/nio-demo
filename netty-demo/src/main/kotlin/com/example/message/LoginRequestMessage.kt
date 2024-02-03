package com.example.message

data class LoginRequestMessage(
    var username: String,
    var password: String,
) : Message() {
    override fun getMessageType(): Int {
        return LOGIN_REQUEST_MESSAGE
    }

}