package com.example.razorpayassessment.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.example.razorpayassessment.data.TaskDao
import com.example.razorpayassessment.network.ApiService
import com.example.razorpayassessment.analytics.FirebaseAnalyticsHelper
import com.example.razorpayassessment.ui.theme.RazorpayAssessmentTheme
import com.example.razorpayassessment.viewmodel.TaskViewModel
import com.example.razorpayassessment.viewmodel.TaskViewModelFactory
import com.example.razorpayassessment.data.AppDatabase
import com.example.razorpayassessment.data.Task
import com.google.firebase.perf.FirebasePerformance
import com.google.firebase.perf.metrics.Trace
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Initialize the Room Database and get the TaskDao
        val db = AppDatabase.getDatabase(applicationContext)
        val taskDao = db.taskDao()
        val myTrace: Trace = FirebasePerformance.getInstance().newTrace("my_custom_trace")
        myTrace.start()
        // Initialize ApiService and FirebaseAnalyticsHelper
        val apiService = ApiService.create()  // Create ApiService instance
        val analyticsHelper = FirebaseAnalyticsHelper(this)

        // Create the ViewModel using the ViewModelFactory
        val viewModelFactory = TaskViewModelFactory(taskDao, apiService, analyticsHelper)
        val taskViewModel = ViewModelProvider(this, viewModelFactory).get(TaskViewModel::class.java)

        setContent {
            RazorpayAssessmentTheme {
                var showDialog by remember { mutableStateOf(false) }
                var showEditDialog by remember { mutableStateOf(false) }
                var taskToEdit by remember { mutableStateOf<Task?>(null) }
                val snackbarHostState = remember { SnackbarHostState() }

                Scaffold(
                    snackbarHost = { SnackbarHost(snackbarHostState) },
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = {
                                Text("Task Manager")
                            },
                            actions = {
                                Button(
                                    onClick = { showDialog = true },
                                    modifier = Modifier.padding(8.dp)
                                ) {
                                    Text("Add Task")
                                }
                            }
                        )
                    }
                ) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        TaskListScreen(
                            tasks = taskViewModel.tasks.collectAsState().value,
                            onTaskClick = { task ->
                                taskToEdit = task
                                showEditDialog = true
                            },
                            onAddTask = { showDialog = true },
                            onMarkTaskCompleted = { task -> taskViewModel.markTaskAsCompleted(task) },
                            modifier = Modifier.padding(16.dp)
                        )

                        // Button to simulate a database error
                        Button(onClick = { taskViewModel.simulateDatabaseError() }) {
                            Text("Simulate Database Error")
                        }

                        if (showDialog) {
                            AddTaskDialog(
                                onAddTask = { task ->
                                    taskViewModel.addTask(task)
                                    showDialog = false
                                    CoroutineScope(Dispatchers.Main).launch {
                                        snackbarHostState.showSnackbar("Task added successfully")
                                    }
                                },
                                onDismiss = { showDialog = false }
                            )
                        }

                        if (showEditDialog && taskToEdit != null) {
                            EditTaskDialog(
                                task = taskToEdit!!,
                                onEditTask = { updatedTask ->
                                    taskViewModel.updateTask(updatedTask)
                                    showEditDialog = false
                                    CoroutineScope(Dispatchers.Main).launch {
                                        snackbarHostState.showSnackbar("Task updated successfully")
                                    }
                                },
                                onDismiss = { showEditDialog = false }
                            )
                        }
                    }
                }
            }
        }
    }
}
