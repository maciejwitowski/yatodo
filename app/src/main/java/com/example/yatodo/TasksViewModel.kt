package com.example.yatodo

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
                tasksRepository.toggleDone(viewAction.taskId, viewAction.isDone)
            }
            is ViewAction.TaskDelete -> {
                tasksRepository.delete(viewAction.taskId)
            }
            is ViewAction.TaskAdd -> {
                tasksRepository.insert(
                    content = viewAction.content,
                    isDone = false
                )
            }
        }
}

data class ViewState(val tasks: List<TaskData>)

sealed class ViewAction {
    data class TaskToggle(val taskId: Long, val isDone: Boolean) : ViewAction()
    data class TaskDelete(val taskId: Long) : ViewAction()
    data class TaskAdd(val content: String) : ViewAction()
}