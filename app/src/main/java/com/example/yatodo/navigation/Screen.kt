package com.example.yatodo.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.yatodo.R

enum class Screen(
    val route: String,
    @StringRes val resourceId: Int,
    @DrawableRes val icon: Int
) {
    Tasks(
        "tasks",
        R.string.tasks_screen_title,
        R.drawable.ic_baseline_list_24
    ),

    Today(
        "today",
        R.string.today_screen_title,
        R.drawable.ic_baseline_calendar_today_24
    );

    companion object {
        fun findByRoute(route: String) = values().find { it.route == route }
    }
}
