package com.example.yatodo.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.yatodo.db.daos.TaskDao
import com.example.yatodo.db.entities.TaskEntity

@Database(entities = [TaskEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}