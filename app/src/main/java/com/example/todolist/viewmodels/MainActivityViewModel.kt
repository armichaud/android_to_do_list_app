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
    private val _listItems = MutableStateFlow(listOf<ListItem>(ListItem(id = 100, "Item 1"), ListItem(id = 100, "Item 2")))
    val listItems: StateFlow<List<ListItem>> = _listItems.asStateFlow()

    private var nextId = 0;

    fun addListItem(item: String) {
        _listItems.update { list ->
            list + listOf(ListItem(id = nextId, label = item))
        }
        nextId += 1
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