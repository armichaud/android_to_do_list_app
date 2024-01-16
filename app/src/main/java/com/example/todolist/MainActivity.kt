package com.example.todolist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.room.Room
import com.example.todolist.composables.Main
import com.example.todolist.composables.ToDoList
import com.example.todolist.databases.AppDatabase
import com.example.todolist.ui.theme.ToDoListTheme

class MainActivity : ComponentActivity() {
    private val db: AppDatabase = Room.databaseBuilder(
        applicationContext,
        AppDatabase::class.java, "database"
    ).build()
    val listDao = db.listDao()
    val listItemDao = db.listItemDao()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        setContent {
            ToDoListTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text("To Do List") },
                            colors = topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                                titleContentColor = MaterialTheme.colorScheme.onPrimary
                            )
                        )
                    }
                ) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) { Main() }
                }
            }
        }
    }
}

