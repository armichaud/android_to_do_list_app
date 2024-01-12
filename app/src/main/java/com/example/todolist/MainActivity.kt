package com.example.todolist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todolist.ui.theme.ToDoListTheme
import com.example.todolist.viewmodels.MainActivityViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ToDoListTheme {
                // A surface container using the 'background' color from the theme
                ToDoList(modifier = Modifier.fillMaxSize())
            }
        }
    }
}

@Composable
fun ToDoList(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.background,
    viewModel: MainActivityViewModel = viewModel(),
) {
    val listItems by viewModel.listItems.collectAsStateWithLifecycle()
    Column {
        listItems.map {
            Text(
                text = it.label
            )
        }
    }
}

@Preview
@Composable
fun GreetingPreview() {
    ToDoListTheme {
        ToDoList()
    }
}