package com.example.todolist.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class ToDoList(
    val id: Int,
    val name: String
)

class MainActivityViewModel: ViewModel() {
    private val _lists = MutableStateFlow(listOf<ToDoList>())
    val lists: StateFlow<List<ToDoList>> = _lists
}