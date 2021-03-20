package com.example.yatodo

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import com.example.yatodo.ui.theme.YatodoTheme

@Composable
fun AppContent() {
    YatodoTheme {
        Surface(color = MaterialTheme.colors.background) {
            TasksScreen(
                listOf(
                    TaskData("Call him", false),
                    TaskData("Visit her", true)
                )
            )
        }
    }
}
