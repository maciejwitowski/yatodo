package com.example.yatodo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.plus
import javax.inject.Inject

@FlowPreview
@ExperimentalCoroutinesApi
@HiltViewModel
class TasksViewModel @Inject constructor(
    private val tasksInteractor: TasksInteractor
) : ViewModel() {
    private val viewActions = ConflatedBroadcastChannel<ViewAction>()

    val viewState = MutableLiveData(ViewState(emptyList()))

    init {
        tasksInteractor
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
                tasksInteractor.toggleDone(viewAction.taskId, viewAction.isDone)
            }
            is ViewAction.TaskDelete -> {
                tasksInteractor.delete(viewAction.taskId)
            }
            is ViewAction.TaskAdd -> {
                tasksInteractor.insert(
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