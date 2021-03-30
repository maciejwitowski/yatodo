package main.java.com.example.server.tasks

import main.java.com.example.server.CreateTaskParams

class CreateTask {
    fun perform(
        params: CreateTaskParams
    ): String {
        params.content
        return "success"
    }
}
