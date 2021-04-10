package com.example.yatodo.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.yatodo.R

sealed class Screen(
    val route: String,
    @StringRes val resourceId: Int,
    @DrawableRes val icon: Int
) {
    object Tasks : Screen(
        "tasks",
        R.string.tasks_screen_title,
        R.drawable.ic_baseline_list_24
    )

    object Today : Screen(
        "today",
        R.string.today_screen_title,
        R.drawable.ic_baseline_calendar_today_24
    )
}
