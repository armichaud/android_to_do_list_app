package com.example.todolist.viewmodels

import androidx.lifecycle.ViewModel
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
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

@Entity(
    tableName = "list_item",
    foreignKeys = [ForeignKey(
        entity = ListItem::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("list_id")
    )]
)
data class ListItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val label: String,
    var status: Status = Status.Todo,
    @ColumnInfo(name = "list_id") val listId: Int
)

data class UiState(
    val parentList: ToDoList?,
    val listItems: List<ListItem>,
    val currentInput: String,
    val completedItemCount: Int,
)

class ToDoListViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(
        UiState(parentList = null, listItems = listOf(), currentInput =  "", completedItemCount = 0)
    )
    val uiState: StateFlow<UiState> = _uiState

    fun updateCurrentInput(input: String) {
        _uiState.update { it.copy(currentInput = input) }
    }

    private fun clearCurrentInput() {
        _uiState.update { it.copy(currentInput = "") }
    }

    private fun sortListItems(listItems: List<ListItem>): List<ListItem> =
        listItems.sortedBy { it.status.ordinal }

    private fun addListItem() {
        _uiState.update { currentState ->
            currentState.parentList?.id?.let {
                val updatedList = currentState.listItems +
                        listOf(ListItem(label = currentState.currentInput, listId = it))
                currentState.copy(listItems = sortListItems(updatedList))
            } ?: currentState
        }
    }

    fun handleDeselect() {
        if (_uiState.value.currentInput.isNotBlank()) {
            addListItem()
            clearCurrentInput()
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
            currentState.copy(listItems = sortListItems(updatedList), completedItemCount = updatedCompletedItemCount)
        }
    }
}