package com.example.server

import io.ktor.application.*
import io.ktor.features.*
import main.java.com.example.server.tasks

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    install(CallLogging)

    tasks()
}

