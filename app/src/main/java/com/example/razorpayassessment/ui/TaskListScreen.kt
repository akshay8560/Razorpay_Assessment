package com.example.razorpayassessment.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.razorpayassessment.data.Task

@Composable
fun TaskListScreen(
    tasks: List<Task>,
    onTaskClick: (Task) -> Unit,
    onAddTask: () -> Unit,
    onMarkTaskCompleted: (Task) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        if (tasks.isEmpty()) {
            Text("No tasks available", modifier = modifier.padding(16.dp))
        } else {
            LazyColumn(modifier = modifier.fillMaxSize()) {
                items(tasks) { task ->
                    TaskItem(
                        task = task,
                        onClick = { onTaskClick(task) },
                        onMarkCompleted = { onMarkTaskCompleted(task) }
                    )
                }
            }
        }
    }
}

@Composable
fun TaskItem(task: Task, onClick: () -> Unit, onMarkCompleted: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp)
    ) {
        Text(task.title, modifier = Modifier.weight(1f))
        Checkbox(
            checked = task.isCompleted,
            onCheckedChange = { onMarkCompleted() }
        )
    }
}
