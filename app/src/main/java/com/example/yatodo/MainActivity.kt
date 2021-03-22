package com.example.yatodo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collect

class MainActivity : ComponentActivity() {
    private val tasksViewModel by viewModels<TasksViewModel> { TasksViewModelFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppContent(tasksViewModel)
        }
    }
}

@Suppress("UNCHECKED_CAST")
class TasksViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        require(modelClass == TasksViewModel::class.java)
        return TasksViewModel(TasksRepository()) as T
    }
}