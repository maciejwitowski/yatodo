package com.example.yatodo

import androidx.compose.foundation.layout.Row
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun TaskItem(
    taskData: TaskData
) {
    Row {
        Checkbox(checked = taskData.isDone, onCheckedChange = { /*TODO*/ })
        Text(text = taskData.content)
    }
}