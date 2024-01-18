package com.example.todolist.viewmodels

import androidx.lifecycle.ViewModel
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.todolist.repositories.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
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

    private val _uiState = MutableStateFlow(HomeUiState(newListInput = ""))
    val uiState: StateFlow<HomeUiState> = _uiState

    fun updateNewListInput(newInput: String) {
        _uiState.update { it.copy(newListInput = newInput) }
    }

    private fun clearInput() {
        _uiState.update { it.copy(newListInput = "") }
    }

    fun newList() {
        repository.newList(_uiState.value.newListInput)
        clearInput()
    }
}