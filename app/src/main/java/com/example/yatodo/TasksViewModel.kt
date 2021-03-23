package com.example.yatodo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.launch

@FlowPreview
@ExperimentalCoroutinesApi
class TasksViewModel(
    private val tasksRepository: TasksRepository
) : ViewModel() {
    private val viewActions = ConflatedBroadcastChannel<ViewAction>()

    val viewState = MutableLiveData(ViewState(emptyList()))

    init {
        viewModelScope.launch {
            tasksRepository
                .getTasks()
                .flatMapLatest { tasks ->
                    viewActions
                        .asFlow()
                        .scan(ViewState(tasks)) { state, viewAction ->
                            state.applyViewAction(viewAction)
                        }
                }
                .collect { state ->
                    viewState.value = state
                }
        }
    }

    fun onViewAction(viewAction: ViewAction) {
        viewActions.offer(viewAction)
    }
}

private fun ViewState.applyViewAction(viewAction: ViewAction): ViewState =
    when (viewAction) {
        is ViewAction.TaskToggle -> {
            val updatedTasks = tasks.toggleTask(viewAction.taskId, viewAction.isDone)
            ViewState(updatedTasks)
        }
        is ViewAction.TaskDelete -> {
            val updatedTasks = tasks.filter { it.id != viewAction.taskId }
            ViewState(updatedTasks)
        }
        is ViewAction.TaskAdd -> {
            val highestId = tasks.map { it.id }.maxOrNull() ?: 1
            val newTask = TaskData(
                id = highestId.inc(),
                content = viewAction.content,
                isDone = false
            )
            ViewState(tasks + newTask)
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