package com.example.todolist.composables

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.todolist.viewmodels.MainActivityViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Home(viewModel: MainActivityViewModel = hiltViewModel(), goToList: (Int) -> Unit) {
    val lists by viewModel.lists.collectAsStateWithLifecycle(emptyList())
    LazyColumn {
        items(lists) {
            ListItem(
                modifier = Modifier.animateItemPlacement(),
                headlineContent = {
                    TextButton(
                        onClick = {
                            viewModel.updateTitle(it.name)
                            goToList(it.id)
                        }
                    ) {
                        Text(it.name)
                    } },
                trailingContent = {
                    IconButton(
                        onClick = { viewModel.deleteList(it.id) }
                    ) { Icon(Icons.Outlined.Delete, "Delete list") }
                }
            )
        }
    }
}

@Preview
@Composable
fun MainPreview() {
    Home() {}
}
