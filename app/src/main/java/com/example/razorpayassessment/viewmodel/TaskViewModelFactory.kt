package com.example.razorpayassessment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.razorpayassessment.data.TaskDao
import com.example.razorpayassessment.network.ApiService
import com.example.razorpayassessment.analytics.FirebaseAnalyticsHelper

class TaskViewModelFactory(
    private val taskDao: TaskDao,
    private val apiService: ApiService,
    private val analyticsHelper: FirebaseAnalyticsHelper
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TaskViewModel(taskDao, apiService, analyticsHelper) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
