package com.example.todolist.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.todolist.models.ListItem
import com.example.todolist.repositories.AppRepository
import com.example.todolist.utils.Status
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class UiState(
    val currentInput: String,
    val completedItemCount: Int,
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

    @Suppress("UNCHECKED_CAST")
    companion object {
        fun providesFactory(
            assistedFactory: ToDoListViewModelFactory,
            parentListId: Int
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(parentListId) as T
            }
        }
    }

    private val _uiState = MutableStateFlow(
        UiState(currentInput =  "", completedItemCount = 0)
    )
    val uiState: StateFlow<UiState> = _uiState

    val listItems = repository.getListContents(parentListId)

    fun updateCurrentInput(input: String) {
        _uiState.update { it.copy(currentInput = input) }
    }

    private fun clearCurrentInput() {
        _uiState.update { it.copy(currentInput = "") }
    }

    private fun sortListItems(listItems: List<ListItem>): List<ListItem> =
        listItems.sortedBy { it.status.ordinal }

    private fun addListItem() {
        viewModelScope.launch {
            repository.addListItem(ListItem(label = _uiState.value.currentInput, listId = parentListId))
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
}