package com.example.message

data class GroupJoinRequestMsg(
    val group:String
):Message() {
    override fun getMessageType(): Int {
        return GROUP_JOIN_REQUEST_MESSAGE
    }
}