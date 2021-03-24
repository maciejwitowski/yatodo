package com.example.yatodo.di

import android.content.Context
import androidx.room.Room
import com.example.yatodo.db.AppDatabase

interface AppContainer {
    val database: AppDatabase
}

class AppContainerImpl(private val appContext: Context) : AppContainer {
    override val database: AppDatabase by lazy {
        Room
            .databaseBuilder(
                appContext,
                AppDatabase::class.java, "yatodo-database"
            )
            .build()
    }
}

