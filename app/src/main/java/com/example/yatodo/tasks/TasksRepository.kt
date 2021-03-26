package com.example.yatodo.tasks

import com.example.db.dao.TaskDao
import com.example.db.entity.TaskEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TasksRepository @Inject constructor(
    private val taskDao: TaskDao
) {
    suspend fun insert(
        content: String,
        isDone: Boolean
    ) {
        taskDao.insert(TaskEntity(0, content, isDone))
    }

    suspend fun toggleDone(
        taskId: Long,
        isDone: Boolean
    ) {
        taskDao.toggleDone(taskId, isDone)
    }

    suspend fun delete(taskId: Long) {
        taskDao.delete(taskId)
    }

    fun getAllTasks(): Flow<List<TaskData>> =
        taskDao
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