package com.example.todolist.viewmodels

import androidx.lifecycle.ViewModel
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Entity(tableName = "to_do_list")
data class ToDoList(
    @PrimaryKey val id: Int,
    val name: String
)

class MainActivityViewModel: ViewModel() {
    private val _lists = MutableStateFlow(listOf<ToDoList>())
    val lists: StateFlow<List<ToDoList>> = _lists
}