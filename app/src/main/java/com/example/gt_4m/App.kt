package com.example.gt_4m

import android.app.Application
import androidx.room.Room
import com.example.gt_4m.data.local.room.AppDatabase

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database-name"
        ).allowMainThreadQueries().build()
    }


    companion object {
        lateinit var db: AppDatabase
    }
}