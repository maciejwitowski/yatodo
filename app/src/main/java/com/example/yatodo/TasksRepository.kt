package com.example.yatodo

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class TasksRepository {
    fun getTasks(): Flow<List<TaskData>> =
        flowOf(
            listOf(
                TaskData(
                    id = 1,
                    content = "Call him",
                    isDone = false
                ),
                TaskData(
                    id = 2,
                    content = "Visit her",
                    isDone = true
                )
            )
        )
}