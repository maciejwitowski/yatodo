package com.example.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.db.dao.TaskDao
import com.example.db.entity.TaskEntity

@Database(entities = [TaskEntity::class], version = 1)
internal abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}