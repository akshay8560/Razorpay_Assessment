package com.example.razorpayassessment.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.example.razorpayassessment.data.TaskDao
import com.example.razorpayassessment.network.ApiService
import com.example.razorpayassessment.analytics.FirebaseAnalyticsHelper
import com.example.razorpayassessment.ui.theme.RazorpayAssessmentTheme
import com.example.razorpayassessment.viewmodel.TaskViewModel
import com.example.razorpayassessment.viewmodel.TaskViewModelFactory
import com.example.razorpayassessment.data.AppDatabase

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Initialize the Room Database and get the TaskDao
        val db = AppDatabase.getDatabase(applicationContext)
        val taskDao = db.taskDao()

        // Initialize ApiService and FirebaseAnalyticsHelper
        val apiService = ApiService.create()  // Create ApiService instance
        val analyticsHelper = FirebaseAnalyticsHelper(this)

        // Create the ViewModel using the ViewModelFactory
        val viewModelFactory = TaskViewModelFactory(taskDao, apiService, analyticsHelper)
        val taskViewModel = ViewModelProvider(this, viewModelFactory).get(TaskViewModel::class.java)

        setContent {
            RazorpayAssessmentTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TaskListScreen(
                        tasks = taskViewModel.tasks.collectAsState().value,
                        onTaskClick = { task -> /* Handle task click */ },
                        modifier = Modifier.padding(innerPadding) // Apply innerPadding here
                    )
                }
            }
        }
    }
}
