package com.example.gt_4m.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.gt_4m.model.Task

@Database(entities = [Task::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}