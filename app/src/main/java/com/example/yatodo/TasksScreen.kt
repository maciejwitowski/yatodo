package com.example.yatodo

import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@ExperimentalCoroutinesApi
@FlowPreview
@Composable
fun TasksScreen(
    viewModel: TasksViewModel
) {
    val viewState: ViewState by viewModel.viewState.observeAsState(ViewState(emptyList()))
    TasksContent(
        viewState,
        onTaskToggle = { taskId, isDone ->
            viewModel.onViewAction(
                ViewAction.TaskToggle(
                    taskId,
                    isDone
                )
            )
        },
        onTaskDelete = { taskId ->
            viewModel.onViewAction(ViewAction.TaskDelete(taskId))
        }
    )
}

@Composable
fun TasksContent(
    viewState: ViewState,
    onTaskToggle: (Long, Boolean) -> Unit,
    onTaskDelete: (Long) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.tasks_screen_title))
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { /*TODO*/ }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_add_24),
                    contentDescription = null
                )
            }
        }
    ) {
        Column {
            for (task in viewState.tasks) {
                TaskItem(
                    taskData = task,
                    onTaskToggle = { isDone -> onTaskToggle(task.id, isDone) },
                    onTaskDelete = { onTaskDelete(task.id) }
                )
            }
        }
    }
}