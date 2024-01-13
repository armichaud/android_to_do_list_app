package com.example.todolist.composables

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todolist.ui.theme.ToDoListTheme
import com.example.todolist.viewmodels.MainActivityViewModel
import com.example.todolist.viewmodels.Status

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ToDoList(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.background,
    viewModel: MainActivityViewModel = viewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val toDoItemCount = uiState.listItems.size - uiState.completedItemCount
    LazyColumn(modifier = modifier.fillMaxWidth()) {
        item {
            Text(
                modifier = modifier.absolutePadding(top = 10.dp, bottom = 10.dp, left = 16.dp),
                text = "To Do",
                style = MaterialTheme.typography.titleLarge
            )
        }
        if (toDoItemCount == 0) {
            item {
                NewItemInput(
                    modifier = modifier,
                    uiState = uiState,
                    updateCurrentInput = { viewModel.updateCurrentInput(it) },
                    handleDeselect = { viewModel.handleDeselect() }
                )
            }
        }
        itemsIndexed(uiState.listItems, key = { index, item -> item.id }) { index, item ->
            val done = item.status == Status.Done
            if (uiState.completedItemCount > 0 && index == toDoItemCount) {
                Text(
                    modifier = modifier.absolutePadding(top = 10.dp, bottom = 10.dp, left = 16.dp),
                    text = "Done",
                    style = MaterialTheme.typography.titleLarge
                )
            }
            ListItem(
                modifier = modifier.animateItemPlacement(),
                headlineContent = {
                    Text(
                        text = item.label, textDecoration = if (done) {
                            TextDecoration.LineThrough
                        } else {
                            TextDecoration.None
                        }
                    )
                },
                leadingContent = {
                    IconToggleButton(
                        checked = done,
                        onCheckedChange = { viewModel.toggleItemStatus(item.id) }
                    ) {
                        if (done) {
                            Icon(Icons.Filled.CheckCircle, contentDescription = "Marked as done")
                        } else {
                            Icon(Icons.Outlined.CheckCircle, contentDescription = "Mark as done")
                        }
                    }
                },
                trailingContent = {
                    if (!done) {
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
                }
            )
            if (index == toDoItemCount - 1) {
                NewItemInput(
                    modifier = modifier,
                    uiState = uiState,
                    updateCurrentInput = { viewModel.updateCurrentInput(it) },
                    handleDeselect = { viewModel.handleDeselect() }
                )
            }
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