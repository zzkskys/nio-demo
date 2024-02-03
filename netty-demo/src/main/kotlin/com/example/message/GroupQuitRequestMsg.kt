package com.example.message

data class GroupQuitRequestMsg (
    val group:String
):Message() {
    override fun getMessageType(): Int {
        return GROUP_QUIT_REQUEST_MESSAGE
    }
}