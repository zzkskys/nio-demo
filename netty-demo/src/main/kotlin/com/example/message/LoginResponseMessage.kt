package com.example.message

data class LoginResponseMessage(
    var success: Boolean,

    var reason: String = "登录成功"
) : Message() {
    override fun getMessageType(): Int {
        return LOGIN_RESPONSE_MESSAGE
    }
}