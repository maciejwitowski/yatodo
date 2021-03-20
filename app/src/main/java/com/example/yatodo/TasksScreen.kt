package com.example.yatodo

import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource

@Composable
fun TasksScreen(tasks: List<TaskData>) {
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
            for (task in tasks) {
                TaskItem(taskData = task)
            }
        }
    }
}