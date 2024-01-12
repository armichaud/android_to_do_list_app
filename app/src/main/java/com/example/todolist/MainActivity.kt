package com.example.todolist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Delete
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todolist.ui.theme.ToDoListTheme
import com.example.todolist.viewmodels.ListItem
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
        val toDoItems: List<ListItem> = listItems.filter { it.status == Status.Todo }
        val completedItems: List<ListItem> = listItems.filter { it.status == Status.Done }
        Text(
            modifier = modifier.absolutePadding(top = 10.dp, bottom = 10.dp, left = 16.dp),
            text = "To Do",
            style = MaterialTheme.typography.titleLarge
        )
        toDoItems.forEach {item ->
            ListItem(
                headlineContent = {
                    Text(text = item.label)
                },
                leadingContent = {
                    IconToggleButton(
                        checked = false,
                        onCheckedChange = { viewModel.toggleItemStatus(item.id) }
                    ) {
                       Icon(Icons.Outlined.CheckCircle, contentDescription = "Mark as done")
                    }
                },
                trailingContent = {
                    IconButton(
                        content = {
                            Icon(
                                Icons.Outlined.Delete,
                                contentDescription = "Delete item ${item.label}"
                            )
                        },
                        onClick = { viewModel.removeListItem(item.id) },
                    )
                }
            )
        }
        TextField(
            modifier = modifier.fillMaxWidth(),
            shape = RectangleShape,
            value = currentInput,
            maxLines = 1,
            onValueChange = { target: String -> viewModel.updateCurrentInput(target) },
            placeholder = { Text("New item") },
            leadingIcon = {
                IconButton(
                    modifier = modifier.absolutePadding(left = 16.dp),
                    onClick = { viewModel.handleDeselect() }
                ) { Icon(Icons.Outlined.Add, contentDescription = "New list item icon") }
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions( onDone = { viewModel.handleDeselect() })
        )
        Text(
            modifier = modifier.absolutePadding(top = 10.dp, bottom = 10.dp, left = 16.dp),
            text = "Done",
            style = MaterialTheme.typography.titleLarge
        )
        completedItems.forEach {item ->
            ListItem(
                headlineContent = {
                    Text(text = item.label, textDecoration = TextDecoration.LineThrough)
                },
                leadingContent = {
                    IconToggleButton(
                        checked = true,
                        onCheckedChange = { viewModel.toggleItemStatus(item.id) }
                    ) {
                        Icon(Icons.Filled.CheckCircle, contentDescription = "Marked as done")
                    }
                },
            )
        }

    }
}

@Preview
@Composable
fun ToDoListPreview() {
    ToDoListTheme {
        ToDoList()
    }
}