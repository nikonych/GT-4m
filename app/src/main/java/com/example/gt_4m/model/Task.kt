package com.example.gt_4m.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Task(
    @PrimaryKey(autoGenerate = true)
    var id:Int? = null,
    val title: String? = null,
    val description: String? = null
) : java.io.Serializable
