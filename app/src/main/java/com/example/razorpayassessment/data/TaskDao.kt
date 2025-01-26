package com.example.razorpayassessment.data

import androidx.room.*

@Dao
interface TaskDao {

    // Correctly return List<Task> to allow Room to map the query results
    @Query("SELECT * FROM tasks")
     fun getAllTasks(): List<Task>  // Make sure it's List<Task>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insertTask(task: Task)

    @Update
     fun updateTask(task: Task)

    @Delete
     fun deleteTask(task: Task)
}
