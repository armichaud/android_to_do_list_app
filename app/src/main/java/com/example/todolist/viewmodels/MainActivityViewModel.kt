package com.example.todolist.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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

class MainActivityViewModel: ViewModel() {
    private val _listItems = MutableStateFlow(listOf<ListItem>())
    val listItems: StateFlow<List<ListItem>> = _listItems.asStateFlow()

    private val _currentInput = MutableStateFlow("")
    val currentInput: StateFlow<String> = _currentInput.asStateFlow()

    private var nextId = 0;

    fun updateCurrentInput(input: String) {
        _currentInput.value = input
    }

    private fun clearCurrentInput() {
        _currentInput.value = ""
    }

    private fun incrementId() {
        nextId += 1
    }

    private fun addListItem() {
        _listItems.update { list ->
            list + listOf(ListItem(id = nextId, label = _currentInput.value))
        }
    }

    fun handleDeselect() {
        if (currentInput.value.isNotBlank()) {
            addListItem()
            clearCurrentInput()
            incrementId()
        }
    }

    fun removeListItem(id: Int) {
        _listItems.update { list ->
            list.filter { it.id != id }
        }
    }

    fun toggleItemStatus(id: Int) {
        _listItems.update { list ->
            list.map {
                if (it.id == id) {
                    it.copy(status = it.status.toggle())
                } else {
                    it
                }
            }
        }
    }
}