package com.example.todolist

import android.content.ContextWrapper
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
import com.example.todolist.composables.Navigation
import com.example.todolist.repositories.AppRepository
import com.example.todolist.ui.theme.ToDoListTheme
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.components.ActivityComponent
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity () : ComponentActivity() {
    @Inject lateinit var repository: AppRepository

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        repository.initDb(applicationContext = applicationContext)

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
                    Box(modifier = Modifier.padding(innerPadding)) { Navigation() }
                }
            }
        }
    }
}



