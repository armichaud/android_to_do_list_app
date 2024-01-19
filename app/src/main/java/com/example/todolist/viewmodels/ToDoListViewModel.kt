package com.example.todolist.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.models.ListItem
import com.example.todolist.repositories.AppRepository
import com.example.todolist.utils.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class UiState(
    val parentListId: Int,
    val currentInput: String,
    val completedItemCount: Int,
    val listItems: List<ListItem> = emptyList()
)

@HiltViewModel
class ToDoListViewModel @Inject constructor(private var repository: AppRepository): ViewModel() {
    private val _uiState = MutableStateFlow(
        UiState(parentListId = -1, currentInput =  "", completedItemCount = 0)
    )
    val uiState: StateFlow<UiState> = _uiState

    private fun updateListContents() {
        _uiState.update {
            it.copy(listItems = repository.getListContents(_uiState.value.parentListId))
        }
    }

    fun updateCurrentInput(input: String) {
        _uiState.update { it.copy(currentInput = input) }
    }

    private fun clearCurrentInput() {
        _uiState.update { it.copy(currentInput = "") }
    }

    private fun sortListItems(listItems: List<ListItem>): List<ListItem> =
        listItems.sortedBy { it.status.ordinal }

    private fun addListItem() {
        val currentState = _uiState.value
        viewModelScope.launch {
            repository.addListItem(ListItem(label = currentState.currentInput, listId = currentState.parentListId))
        }
    }

    fun handleNewInput() {
        if (_uiState.value.currentInput.isNotBlank()) {
            addListItem()
            clearCurrentInput()
        }
    }

    fun removeListItem(id: Int) {
        viewModelScope.launch {
            repository.deleteListItemById(id)
        }
    }
    fun toggleItemStatus(item: ListItem) {
        val currentState = _uiState.value
        var updatedCompletedItemCount = currentState.completedItemCount
        val updatedStatus = item.status.toggle()
        updatedCompletedItemCount += if (updatedStatus == Status.Todo) { -1 } else { 1 }
        _uiState.update { it.copy(completedItemCount = updatedCompletedItemCount) }
        viewModelScope.launch {
            repository.updateListItemStatus(item.id, updatedStatus)
        }
    }

    fun setParentList(parentListId: Int) {
        _uiState.update { it.copy(parentListId = parentListId) }
        updateListContents()
    }
}