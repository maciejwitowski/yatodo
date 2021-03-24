package com.example.yatodo

import com.example.yatodo.db.AppDatabase
import com.example.yatodo.db.entities.TaskEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TasksRepository(
    private val appDatabase: AppDatabase
) {
    suspend fun insert(taskData: TaskData) { // TODO Move out of main thread by using suspend
        appDatabase.taskDao().insert(TaskEntity(taskData.content, taskData.isDone))
    }

    fun getAllTasks(): Flow<List<TaskData>> =
        appDatabase
            .taskDao()
            .getAll()
            .map { taskEntities ->
                taskEntities.map(TaskEntity::toTaskData)
            }
}

private fun TaskEntity.toTaskData() =
    TaskData(
        content = content,
        isDone = isDone
    )