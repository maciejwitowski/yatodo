package com.example.yatodo

import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.yatodo.navigation.Screen
import com.example.yatodo.tasks.TasksScreen
import com.example.yatodo.tasks.TasksViewModel
import com.example.yatodo.tasks.ViewState
import com.example.yatodo.today.TodayScreen
import com.example.yatodo.ui.theme.YatodoTheme
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@ExperimentalCoroutinesApi
@FlowPreview
@Composable
fun AppContent(viewModel: TasksViewModel) {
    YatodoTheme {
        val viewState: ViewState by viewModel.viewState.observeAsState(ViewState(emptyList()))
        var selected: Screen by remember { mutableStateOf(Screen.Tasks) }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = stringResource(selected.resourceId))
                    }
                )
            },
            bottomBar = {
                BottomBar(selected = selected) { screen ->
                    selected = screen
                }
            }
        ) {
            when (selected) {
                Screen.Tasks -> TasksScreen(viewState.tasks) { viewAction ->
                    viewModel.onViewAction(
                        viewAction
                    )
                }
                Screen.Today -> TodayScreen()
            }
        }
    }
}

@Composable
fun BottomBar(selected: Screen, onClick: (Screen) -> Unit) {
    BottomNavigation {
        val screens = listOf(Screen.Tasks, Screen.Today)

        screens.forEach { screen ->
            BottomNavigationItem(
                selected = selected == screen,
                icon = {
                    Icon(
                        painter = painterResource(id = screen.icon),
                        contentDescription = null
                    )
                },
                label = { Text(stringResource(screen.resourceId)) },
                onClick = { onClick(screen) }
            )
        }
    }
}
