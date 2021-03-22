package com.example.yatodo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class TasksViewModel(
    private val tasksRepository: TasksRepository
) : ViewModel() {
    private val _viewState = MutableStateFlow(ViewState(emptyList()))
    val viewState: StateFlow<ViewState> = _viewState

    init {
        viewModelScope.launch {
            tasksRepository
                .getTasks()
                .collect { tasks ->
                    _viewState.value = ViewState(tasks)
                }
        }
    }
}

data class ViewState(val tasks: List<TaskData>)

sealed class ViewAction {
    data class TaskDoneToggled(val isDone: Boolean) : ViewAction()
}