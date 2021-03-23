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
        is ViewAction.TaskToggled -> {
            val updatedTasks = tasks.toggleTask(viewAction.taskId, viewAction.isDone)
            ViewState(updatedTasks)
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
    data class TaskToggled(val taskId: Long, val isDone: Boolean) : ViewAction()
}