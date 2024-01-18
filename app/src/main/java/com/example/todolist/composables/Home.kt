package com.example.todolist.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.todolist.viewmodels.MainActivityViewModel

@Composable
fun Home(viewModel: MainActivityViewModel = hiltViewModel(), openList: (Int) -> Unit) {

    val lists by viewModel.lists.collectAsStateWithLifecycle()
    viewModel.loadLists()

    Box {
        LazyColumn {
            items(lists) {
                ListItem(headlineContent = { Text(it.name) })
            }
        }
        ExtendedFloatingActionButton(
            onClick = { /*Todo*/ },
            icon = { Icon(Icons.Filled.Edit, "Create new to-do list.") },
            text = { Text(text = "New List") },
        )
        Dialog(
            onDismissRequest = { /*Todo*/ }
        ) {
            Card(
                modifier = Modifier.fillMaxWidth().height(375.dp).padding(16.dp),
                shape = RoundedCornerShape(16.dp),
            ) {
                Text("Give your list a name")
                TextField(value = "TODO", onValueChange = {})
                LazyRow {
                    item {
                        TextButton(onClick = {
                            // TODO Close dialog
                        }) {
                            Text("Cancel")
                        }
                    }
                    item{
                        TextButton(onClick = {
                            // TODO Return after creating list
                            openList(-1)
                        }) {
                            Text("Add")
                        }
                    }
                }
            }
        }
    }

}

@Preview
@Composable
fun MainPreview() {
    Home {}
}
