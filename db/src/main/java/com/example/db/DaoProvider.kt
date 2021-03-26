package com.example.db

import android.content.Context
import androidx.room.Room

class DaoProvider(context: Context) {
    private val database by lazy {
        Room
            .databaseBuilder(
                context,
                AppDatabase::class.java, DB_NAME
            )
            .build()
    }

    val tasksDao by lazy { database.taskDao() }

    private companion object {
        const val DB_NAME = "yatodo-database"
    }
}