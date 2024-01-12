package com.example.todolist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todolist.ui.theme.ToDoListTheme
import com.example.todolist.viewmodels.MainActivityViewModel
import com.example.todolist.viewmodels.Status

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ToDoListTheme {
                ToDoList()
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
    val currentInput by viewModel.currentInput.collectAsStateWithLifecycle()
    Column(modifier = modifier.fillMaxWidth()) {
        listItems.forEach {item ->
            val done = item.status == Status.Done
            ListItem(
                headlineContent = {
                    Text(
                        text = item.label,
                        textDecoration = if (done) { TextDecoration.LineThrough } else { TextDecoration.None }
                    )
                },
                leadingContent = {
                    IconToggleButton(
                        checked = item.status == Status.Done,
                        onCheckedChange = { viewModel.toggleItemStatus(item.id) }
                    ) {
                        if (done) {
                            Icon(Icons.Filled.Done, contentDescription = "Marked as done")
                        } else {
                            Icon(Icons.Outlined.CheckCircle, contentDescription = "Mark as done")
                        }
                    }
                }
            )
        }
        TextField(
            modifier = modifier.fillMaxWidth(),
            value = currentInput,
            maxLines = 1,
            onValueChange = { target: String -> viewModel.updateCurrentInput(target) },
            placeholder = { Text("New item") },
            leadingIcon = {
                IconButton(
                    onClick = { viewModel.handleDeselect() }
                ) { Icon(Icons.Outlined.Add, contentDescription = "New list item icon") }
            }
        )
    }
}

@Preview
@Composable
fun ToDoListPreview() {
    ToDoListTheme {
        ToDoList()
    }
}