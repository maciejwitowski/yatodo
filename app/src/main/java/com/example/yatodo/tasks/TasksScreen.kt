package com.example.yatodo.tasks

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.yatodo.R
import com.example.yatodo.tasks.ViewAction.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@ExperimentalCoroutinesApi
@FlowPreview
@Composable
fun TasksScreen(
    tasks: List<TaskData>,
    onViewAction: (ViewAction) -> Unit
) {
    LazyColumn(Modifier.padding(8.dp)) {
        item {
            AddTask { content -> onViewAction(TaskAdd(content)) }
        }

        for (task in tasks) {
            item {
                TaskItem(
                    taskData = task,
                    onTaskToggle = { isDone ->
                        onViewAction(
                            TaskToggle(
                                task.id,
                                isDone
                            )
                        )
                    },
                    onTaskDelete = { onViewAction(TaskDelete(task.id)) }
                )
            }
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