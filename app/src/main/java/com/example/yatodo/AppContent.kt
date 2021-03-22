package com.example.yatodo

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import com.example.yatodo.ui.theme.YatodoTheme

@Composable
fun AppContent(tasksViewModel: TasksViewModel) {
    YatodoTheme {
        Surface(color = MaterialTheme.colors.background) {
            TasksScreen(tasksViewModel.viewState.value.tasks)
        }
    }
}
