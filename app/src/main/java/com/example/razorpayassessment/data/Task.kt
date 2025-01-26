package com.example.razorpayassessment.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,  // Room will use this as the primary key
    val title: String,
    val description: String,
    val isCompleted: Boolean = false
)
