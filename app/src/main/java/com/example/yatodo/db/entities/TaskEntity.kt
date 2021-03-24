package com.example.yatodo.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class TaskEntity(
    @ColumnInfo(name = "content")
    val content: String,

    @ColumnInfo(name = "is_done")
    val isDone: Boolean
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}