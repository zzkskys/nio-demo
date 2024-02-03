package com.example.protocol

import io.netty.handler.codec.LengthFieldBasedFrameDecoder

/**
 * 自定义协议解析器
 * 基于自定义协议而设计
 */
class MessageProcotolDecoder :
    LengthFieldBasedFrameDecoder(1024, 12, 4, 0, 0) 