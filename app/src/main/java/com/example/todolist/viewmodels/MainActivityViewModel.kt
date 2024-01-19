package com.example.todolist.viewmodels

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

const val DEFAULT_TITLE = "To Do Lists"

@HiltViewModel
class MainActivityViewModel @Inject constructor(): ViewModel() {
    private val _title = MutableStateFlow(DEFAULT_TITLE)
    val title: StateFlow<String> = _title

    fun updateTitle(title: String? = DEFAULT_TITLE) {
        if (title != null) {
            _title.value = title
        }
    }
}