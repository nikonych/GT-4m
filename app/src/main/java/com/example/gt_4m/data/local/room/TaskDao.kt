package com.example.gt_4m.data.local.room

import androidx.room.*
import com.example.gt_4m.model.Task

@Dao
interface TaskDao {

    @Query("select * from task order by id desc")
    fun getAll(): MutableList<Task>

    @Insert
    fun insert(task: Task)

    @Delete
    fun delete(task: Task)

    @Update
    fun update(task: Task)
}