package com.example.yatodo

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.yatodo.ViewAction.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@ExperimentalCoroutinesApi
@FlowPreview
@Composable
fun TasksScreen(
    viewModel: TasksViewModel
) {
    val viewState: ViewState by viewModel.viewState.observeAsState(ViewState(emptyList()))

    TasksScreenLayout {
        item {
            AddTask { content -> viewModel.onViewAction(TaskAdd(content)) }
        }

        for (task in viewState.tasks) {
            item {
                TaskItem(
                    taskData = task,
                    onTaskToggle = { isDone ->
                        viewModel.onViewAction(
                            TaskToggle(
                                task.id,
                                isDone
                            )
                        )
                    },
                    onTaskDelete = { viewModel.onViewAction(TaskDelete(task.id)) }
                )
            }
        }
    }
}

@Composable
fun TasksScreenLayout(content: LazyListScope.() -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.tasks_screen_title))
                }
            )
        },
    ) {
        LazyColumn(Modifier.padding(8.dp)) {
            content()
        }
    }
}

@Composable
fun AddTask(
    onTaskAdd: (String) -> Unit
) {
    var content by rememberSaveable { mutableStateOf("") }
    var isError by rememberSaveable { mutableStateOf(false) }

    Row(verticalAlignment = Alignment.CenterVertically) {
        OutlinedTextField(
            value = content,
            onValueChange = {
                isError = false
                content = it
            },
            label = { Text(stringResource(R.string.task_content_field)) },
            isError = isError
        )

        Spacer(modifier = Modifier.width(8.dp))

        Button(
            onClick = {
                if (content.isBlank()) {
                    isError = true
                } else {
                    onTaskAdd(content)
                    content = ""
                }
            },
            content = { Text(text = stringResource(R.string.add_task_button)) }
        )
    }
}