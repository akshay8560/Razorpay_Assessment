package com.example.razorpayassessment.viewmodel

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.razorpayassessment.data.Task
import com.example.razorpayassessment.data.TaskDao
import com.example.razorpayassessment.network.ApiService
import com.example.razorpayassessment.analytics.FirebaseAnalyticsHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TaskViewModel(
    private val taskDao: TaskDao,
    private val apiService: ApiService,
    private val analyticsHelper: FirebaseAnalyticsHelper
) : ViewModel() {
    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> get() = _tasks

    init {
        fetchTasksFromApi()
    }

    private fun fetchTasksFromApi() {
        viewModelScope.launch {
            try {
                val tasksFromApi = apiService.getTasks()
                Log.d("TaskViewModel", "Fetched tasks from API: $tasksFromApi")  // Log the tasks
                withContext(Dispatchers.IO) {
                    tasksFromApi.forEach { task ->
                        // Provide a default description if null
                        val taskWithDefaultDescription = task.copy(
                            description = task.description ?: "No description available"
                        )
                        taskDao.insertTask(taskWithDefaultDescription)
                        Log.d("TaskViewModel", "Inserted task into DB: ${taskWithDefaultDescription.title}")
                    }
                }
                loadTasks()
            } catch (e: Exception) {
                Log.e("TaskViewModel", "Error fetching tasks from API", e)
            }
        }
    }

    fun loadTasks() {
        viewModelScope.launch {
            // Make sure this query is done in the background thread
            val tasksFromDb = withContext(Dispatchers.IO) {
                taskDao.getAllTasks()
            }
            _tasks.value = tasksFromDb
        }
    }

    fun addTask(task: Task) {
        viewModelScope.launch {
            taskDao.insertTask(task)
            analyticsHelper.logEvent("Task Added", Bundle().apply {
                putString("task_title", task.title)
            })
            loadTasks()
        }
    }

    fun updateTask(task: Task) {
        viewModelScope.launch {
            taskDao.updateTask(task)
            analyticsHelper.logEvent("Task Edited", Bundle().apply {
                putString("task_title", task.title)
            })
            loadTasks()
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            taskDao.deleteTask(task)
            loadTasks()
        }
    }

    fun markTaskAsCompleted(task: Task) {
        viewModelScope.launch {
            taskDao.updateTask(task.copy(isCompleted = true))
            analyticsHelper.logEvent("Task Completed", Bundle().apply {
                putString("task_title", task.title)
            })
            loadTasks()
        }
    }
}