package com.example.chat.user

interface UserService {
    fun login(username:String,password:String):Boolean
}


class InMemoryUserService : UserService {

    private val map = mapOf(
        "zhangsan" to "123",
        "lisi" to "123",
        "wangwu" to "123",
        "zhaoliu" to "123",
        "qianqi" to "123"
    )

    override fun login(username: String, password: String): Boolean {
        val find = map[username]

        return if (find != null && find == password) true else false
    }
}