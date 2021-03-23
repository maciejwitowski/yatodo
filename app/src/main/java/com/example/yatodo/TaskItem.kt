package com.example.yatodo

import androidx.compose.foundation.layout.*
import androidx.compose.material.Checkbox
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun TaskItem(
    taskData: TaskData,
    onTaskToggle: (Boolean) -> Unit,
    onTaskDelete: () -> Unit
) {
    Row(Modifier.padding(vertical = 8.dp)) {
        Checkbox(checked = taskData.isDone, onCheckedChange = onTaskToggle)
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = taskData.content)
        Spacer(modifier = Modifier.width(8.dp))
        DeleteTaskButton(onTaskDelete)
    }
}

@Composable
fun DeleteTaskButton(onTaskDelete: () -> Unit) {
    IconButton(
        modifier = Modifier.then(Modifier.size(24.dp)),
        onClick = { onTaskDelete() }
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_baseline_delete_16),
            contentDescription = null,
            tint = Color.Gray
        )
    }
}