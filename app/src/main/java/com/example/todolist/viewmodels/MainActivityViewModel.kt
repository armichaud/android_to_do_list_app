package com.example.todolist.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.models.ToDoList
import com.example.todolist.repositories.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeUiState (
    val newListInput: String,
    val dialogOpen: Boolean = false,
    val selectedList: ToDoList? = null
)

@HiltViewModel
class MainActivityViewModel @Inject constructor(private var repository: AppRepository): ViewModel() {
    val lists = repository.getLists()

    private val _uiState = MutableStateFlow(HomeUiState(newListInput = ""))
    val uiState: StateFlow<HomeUiState> = _uiState

    fun updateNewListInput(newInput: String) {
        _uiState.update { it.copy(newListInput = newInput) }
    }

    fun openDialog() {
        _uiState.update { it.copy(dialogOpen = true) }
    }

    fun closeDialog() {
        _uiState.update { it.copy(dialogOpen = false) }
    }

    private fun clearInput() {
        _uiState.update { it.copy(newListInput = "") }
    }

    fun newList() {
        viewModelScope.launch {
            val id = repository.newList(_uiState.value.newListInput)
            clearInput()
            setSelectedList(id)
        }
    }

    private fun setSelectedList(id: Long) {
        val listToLoad = repository.getList(id)
        _uiState.update { it.copy(selectedList = listToLoad) }
    }
}