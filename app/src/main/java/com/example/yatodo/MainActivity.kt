package com.example.yatodo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.yatodo.db.AppDatabase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
class MainActivity : ComponentActivity() {
    private val tasksViewModel by viewModels<TasksViewModel> {
        TasksViewModelFactory(
            (application as YotodoApp).container.database
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppContent(tasksViewModel)
        }
    }
}

@ExperimentalCoroutinesApi
@FlowPreview
@Suppress("UNCHECKED_CAST")
class TasksViewModelFactory(private val database: AppDatabase) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        require(modelClass == TasksViewModel::class.java)
        return TasksViewModel(TasksRepository(database)) as T
    }
}