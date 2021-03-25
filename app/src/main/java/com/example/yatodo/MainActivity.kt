package com.example.yatodo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.yatodo.db.AppDatabase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val tasksViewModel by viewModels<TasksViewModel >()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppContent(tasksViewModel)
        }
    }
}
