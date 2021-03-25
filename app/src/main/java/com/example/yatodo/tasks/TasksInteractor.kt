package com.example.yatodo.tasks

import androidx.work.OneTimeWorkRequestBuilder
import com.example.yatodo.sync.SyncWorker
import com.example.yatodo.work.WorkManagerAdapter
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TasksInteractor @Inject constructor(
    private val tasksRepository: TasksRepository,
    private val workManagerAdapter: WorkManagerAdapter
) {
    suspend fun insert(
        content: String,
        isDone: Boolean
    ) {
        tasksRepository.insert(content, isDone)
        launchSync()
    }

    suspend fun toggleDone(taskId: Long, done: Boolean) {
        tasksRepository.toggleDone(taskId, done)
        launchSync()
    }

    suspend fun delete(taskId: Long) {
        tasksRepository.delete(taskId)
        launchSync()
    }

    fun getAllTasks(): Flow<List<TaskData>> =
        tasksRepository.getAllTasks()

    private fun launchSync() {
        workManagerAdapter.enqueueUnique(
            "sync",
            OneTimeWorkRequestBuilder<SyncWorker>()
                .build()
        )
    }
}