package com.example.server

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    simple()
}

fun Application.simple() {
    routing {
        get("/") {
            call.respondText("Hello world", ContentType.Text.Plain)
        }
    }
}
