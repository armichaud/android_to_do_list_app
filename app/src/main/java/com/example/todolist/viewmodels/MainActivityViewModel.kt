package com.example.todolist.viewmodels

import androidx.lifecycle.ViewModel
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.todolist.repositories.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@Entity(tableName = "to_do_list")
data class ToDoList(
    @PrimaryKey val id: Int = 0,
    val name: String
)

data class HomeUiState (
    val newListInput: String
)

@HiltViewModel
class MainActivityViewModel @Inject constructor(private var repository: AppRepository): ViewModel() {
    val lists = repository.getLists()

    fun newList(name: String) {
        repository.newList(name)
    }
}