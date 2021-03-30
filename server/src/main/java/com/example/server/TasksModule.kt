package main.java.com.example.server

import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import main.java.com.example.server.tasks.CreateTask
import main.java.com.example.server.util.JsonParser

fun Application.tasks() {
    routing {
        post("/tasks") {
            val params = JsonParser.fromJson<CreateTaskParams>(call.receiveText())!!
            val result = CreateTask().perform(params)
            call.respondText(result)
        }
    }
}

data class CreateTaskParams(val content: String)
