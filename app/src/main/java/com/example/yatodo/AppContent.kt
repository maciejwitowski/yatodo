package com.example.yatodo

import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.*
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
        val navController = rememberNavController()

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentScreen = navBackStackEntry
            ?.arguments
            ?.getString(KEY_ROUTE)
            ?.let { Screen.findByRoute(it) } ?: Screen.Tasks

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = stringResource(currentScreen.resourceId))
                    }
                )
            },
            bottomBar = {
                BottomBar(currentScreen) { screen ->
                    navController.navigate(screen.route) {
                        popUpTo = navController.graph.startDestination
                        launchSingleTop = true
                    }
                }
            }
        ) {
            NavHost(navController, startDestination = Screen.Tasks.route) {
                composable(Screen.Tasks.route) {
                    TasksScreen(viewState.tasks) { viewAction ->
                        viewModel.onViewAction(
                            viewAction
                        )
                    }
                }
                composable(Screen.Today.route) { TodayScreen() }
            }
        }
    }
}

@Composable
fun BottomBar(currentRoute: Screen, onClick: (Screen) -> Unit) {
    BottomNavigation {
        val screens = listOf(Screen.Tasks, Screen.Today)

        screens.forEach { screen ->
            BottomNavigationItem(
                selected = currentRoute == screen,
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
