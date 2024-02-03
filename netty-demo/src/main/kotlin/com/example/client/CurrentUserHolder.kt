package com.example.client

class CurrentUserHolder {
    companion object {

        var username: String = ""

        fun isLogin(): Boolean = username.isNotBlank()

        fun clear() {
            this.username = ""
        }
    }
}