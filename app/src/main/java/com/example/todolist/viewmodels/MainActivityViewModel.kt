package com.example.todolist.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

enum class Status {
    Todo, Done;

    fun toggle(): Status = when (this) {
        Todo -> Done
        Done -> Todo
    }
}

data class ListItem(
    val id: Int,
    val label: String,
    var status: Status = Status.Todo,
)

data class UiState(
    val listItems: List<ListItem>,
    val currentInput: String,
    val completedItemCount: Int,
)

class MainActivityViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(UiState(listItems = listOf(), currentInput =  "", completedItemCount = 0))
    val uiState: StateFlow<UiState> = _uiState

    private var nextId = 0;

    fun updateCurrentInput(input: String) {
        _uiState.update { it.copy(currentInput = input) }
    }

    private fun clearCurrentInput() {
        _uiState.update { it.copy(currentInput = "") }
    }

    private fun incrementId() {
        nextId += 1
    }

    private fun addListItem() {
        _uiState.update { currentState ->
            val updatedList = currentState.listItems + listOf(ListItem(id = nextId, label = currentState.currentInput))
            currentState.copy( listItems = updatedList)
        }
    }

    fun handleDeselect() {
        if (_uiState.value.currentInput.isNotBlank()) {
            addListItem()
            clearCurrentInput()
            incrementId()
        }
    }

    fun removeListItem(id: Int) {
        _uiState.update { currentState ->
            currentState.copy(listItems = currentState.listItems.filter { it.id != id })
        }
    }

    fun toggleItemStatus(id: Int) {
        _uiState.update { currentState ->
            var updatedCompletedItemCount = currentState.completedItemCount
            val updatedList = currentState.listItems.map {
                if (it.id == id) {
                    val updatedStatus = it.status.toggle()
                    updatedCompletedItemCount += if (updatedStatus == Status.Todo) { -1 } else { 1 }
                    it.copy(status = updatedStatus)
                } else {
                    it
                }
            }
            currentState.copy(listItems = updatedList, completedItemCount = updatedCompletedItemCount)
        }
    }
}