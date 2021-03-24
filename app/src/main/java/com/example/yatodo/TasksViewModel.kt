package com.example.yatodo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.plus

@FlowPreview
@ExperimentalCoroutinesApi
class TasksViewModel(
    private val tasksRepository: TasksRepository
) : ViewModel() {
    private val viewActions = ConflatedBroadcastChannel<ViewAction>()

    val viewState = MutableLiveData(ViewState(emptyList()))

    init {
        tasksRepository
            .getAllTasks()
            .onEach { tasks ->
                viewState.value = ViewState(tasks)
            }
            .launchIn(viewModelScope)

        viewActions
            .asFlow()
            .onEach { applyViewAction(it) }
            .launchIn(viewModelScope + Dispatchers.IO)
    }

    fun onViewAction(viewAction: ViewAction) {
        viewActions.offer(viewAction)
    }

    private suspend fun applyViewAction(viewAction: ViewAction) =
        when (viewAction) {
            is ViewAction.TaskToggle -> {
                TODO()
//                val updatedTasks = tasks.toggleTask(viewAction.taskId, viewAction.isDone)
//                ViewState(updatedTasks)
            }
            is ViewAction.TaskDelete -> {
                TODO()
//                val updatedTasks = tasks.filter { it.id != viewAction.taskId }
//                ViewState(updatedTasks)
            }
            is ViewAction.TaskAdd -> {
                tasksRepository.insert(
                    TaskData(
                        content = viewAction.content,
                        isDone = false
                    )
                )
            }
        }
}

private fun List<TaskData>.toggleTask(taskId: Long, isDone: Boolean): List<TaskData> =
    map { task ->
        if (task.id == taskId) {
            task.copy(isDone = isDone)
        } else {
            task
        }
    }

data class ViewState(val tasks: List<TaskData>)

sealed class ViewAction {
    data class TaskToggle(val taskId: Long, val isDone: Boolean) : ViewAction()
    data class TaskDelete(val taskId: Long) : ViewAction()
    data class TaskAdd(val content: String) : ViewAction()
}