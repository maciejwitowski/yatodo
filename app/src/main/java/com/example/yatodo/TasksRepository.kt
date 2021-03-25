package com.example.yatodo

import com.example.yatodo.db.AppDatabase
import com.example.yatodo.db.entities.TaskEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TasksRepository(
    appDatabase: AppDatabase
) {
    private val tasksDao by lazy { appDatabase.taskDao() }

    suspend fun insert(
        content: String,
        isDone: Boolean
    ) {
        tasksDao.insert(TaskEntity(0, content, isDone))
    }

    suspend fun toggleDone(
        taskId: Long,
        isDone: Boolean
    ) {
        tasksDao.toggleDone(taskId, isDone)
    }

    suspend fun delete(taskId: Long) {
        tasksDao.delete(taskId)
    }

    fun getAllTasks(): Flow<List<TaskData>> =
        tasksDao
            .getAll()
            .map { taskEntities ->
                taskEntities.map(TaskEntity::toTaskData)
            }
}

private fun TaskEntity.toTaskData() =
    TaskData(
        id = id,
        content = content,
        isDone = isDone
    )