package com.example.todolist.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.models.ListItem
import com.example.todolist.repositories.AppRepository
import com.example.todolist.utils.Status
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class UiState(
    val currentInput: String,
    val completedItemCount: Int = 0,
)

@HiltViewModel(assistedFactory = ToDoListViewModel.ToDoListViewModelFactory::class)
class ToDoListViewModel @AssistedInject constructor(
    @Assisted private val parentListId: Int,
    private var repository: AppRepository
): ViewModel() {
    @AssistedFactory
    interface ToDoListViewModelFactory {
        fun create(parentListId: Int): ToDoListViewModel
    }

    // Data fetching
    val listItems = repository.getListContents(parentListId)

    private fun addListItem() {
        viewModelScope.launch {
            repository.addListItem(ListItem(label = _uiState.value.currentInput, listId = parentListId))
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
        updateCompletedItemCount(updatedCompletedItemCount)
        viewModelScope.launch {
            repository.updateListItemStatus(item.id, updatedStatus)
        }
    }

    // State
    private val _uiState = MutableStateFlow(UiState(currentInput =  ""))
    val uiState: StateFlow<UiState> = _uiState

    fun updateCurrentInput(input: String) =  _uiState.update { it.copy(currentInput = input) }
    private fun clearCurrentInput() = _uiState.update { it.copy(currentInput = "") }
    fun updateCompletedItemCount(updatedCount: Int) = _uiState.update { it.copy(completedItemCount = updatedCount) }
    fun handleNewInput() {
        if (_uiState.value.currentInput.isNotBlank()) {
            addListItem()
            clearCurrentInput()
        }
    }
}