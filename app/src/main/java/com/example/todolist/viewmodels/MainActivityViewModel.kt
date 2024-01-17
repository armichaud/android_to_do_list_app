package com.example.todolist.viewmodels

import androidx.lifecycle.ViewModel
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.todolist.repositories.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@Entity(tableName = "to_do_list")
data class ToDoList(
    @PrimaryKey val id: Int,
    val name: String
)

@HiltViewModel
class MainActivityViewModel @Inject constructor(private var repository: AppRepository): ViewModel() {

    private val _lists = MutableStateFlow(listOf<ToDoList>())
    val lists: StateFlow<List<ToDoList>> = _lists

    fun loadLists() {
        _lists.value = repository.getLists()
    }

    fun updateLists(lists: List<ToDoList>) {
        _lists.value = lists
    }
}