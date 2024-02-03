package com.example.protocol

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.kotlinModule

class JsonHolder {

    companion object{
        val mapper:ObjectMapper = ObjectMapper().registerModules(kotlinModule())
    }
}