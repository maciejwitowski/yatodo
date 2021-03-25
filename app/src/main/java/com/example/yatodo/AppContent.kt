package com.example.yatodo

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import com.example.yatodo.tasks.TasksScreen
import com.example.yatodo.tasks.TasksViewModel
import com.example.yatodo.ui.theme.YatodoTheme
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@ExperimentalCoroutinesApi
@FlowPreview
@Composable
fun AppContent(tasksViewModel: TasksViewModel) {
    YatodoTheme {
        Surface(color = MaterialTheme.colors.background) {
            TasksScreen(viewModel = tasksViewModel)
        }
    }
}
