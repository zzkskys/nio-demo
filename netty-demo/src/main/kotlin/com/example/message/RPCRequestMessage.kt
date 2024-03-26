package com.example.message

data class RPCRequestMessage(
    var interfaceName: String,
    var methodName: String,
    var returnType: Class<*> = Void::class.java,
    var parameterTypes: Array<Class<*>> = emptyArray(),
    var parameterValues: Array<Any> = emptyArray()
) : Message() {
    override fun getMessageType(): Int {
        return RPC_REQUEST_MESSAGE
    }
}