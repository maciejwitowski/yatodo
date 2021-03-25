package com.example.yatodo.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.yatodo.db.entities.TaskEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Query("select * from tasks")
    fun getAll(): Flow<List<TaskEntity>>

    @Insert
    suspend fun insert(taskEntity: TaskEntity)

    @Query("update tasks set is_done = :isDone where id = :taskId")
    suspend fun toggleDone(taskId: Long, isDone: Boolean)

    @Query("delete from tasks where id = :taskId")
    suspend fun delete(taskId: Long)
}