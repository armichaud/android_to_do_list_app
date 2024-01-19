package com.example.todolist.viewmodels

import androidx.lifecycle.ViewModel
import com.example.todolist.models.ListItem
import com.example.todolist.repositories.AppRepository
import com.example.todolist.utils.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

data class UiState(
    val parentListId: Int,
    val parentListName: String,
    val currentInput: String,
    val completedItemCount: Int,
)

@HiltViewModel
class ToDoListViewModel @Inject constructor(private var repository: AppRepository): ViewModel() {

    private val _uiState = MutableStateFlow(
        UiState(parentListId = -1, parentListName = "", currentInput =  "", completedItemCount = 0)
    )
    val uiState: StateFlow<UiState> = _uiState

    val listItems: Flow<List<ListItem>> =
        repository.getListContents(_uiState.value.parentListId)

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
        if (currentState.currentInput.isNotBlank()) {
            repository.addListItem(ListItem(label = currentState.currentInput, listId = currentState.parentListId))
        }
    }

    fun handleDeselect() {
        if (_uiState.value.currentInput.isNotBlank()) {
            addListItem()
            clearCurrentInput()
        }
    }

    fun removeListItem(id: Int) {
        repository.deleteListItemById(id)
    }
    fun toggleItemStatus(item: ListItem) {
        val currentState = _uiState.value
        var updatedCompletedItemCount = currentState.completedItemCount
        val updatedStatus = item.status.toggle()
        updatedCompletedItemCount += if (updatedStatus == Status.Todo) { -1 } else { 1 }
        _uiState.update { it.copy(completedItemCount = updatedCompletedItemCount) }
        repository.updateListItemStatus(item.id, updatedStatus)
    }

    fun setParentList(parentListId: Int, parentListName: String) {
        _uiState.update { it.copy(parentListId = parentListId, parentListName = parentListName) }
    }
}