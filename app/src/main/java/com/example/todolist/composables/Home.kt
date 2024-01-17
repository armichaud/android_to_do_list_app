package com.example.todolist.composables

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.todolist.viewmodels.MainActivityViewModel

@Composable
fun Home(viewModel: MainActivityViewModel = hiltViewModel(), openList: (Int) -> Unit) {

    val lists by viewModel.lists.collectAsStateWithLifecycle()
    viewModel.loadLists()

    LazyColumn {
        items(lists) {
            ListItem(headlineContent = { Text(it.name) })
        }
    }
}

@Preview
@Composable
fun MainPreview() {
    Home {}
}