package com.example.todolist.viewmodels

import androidx.lifecycle.ViewModel
import com.example.todolist.repositories.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private var repository: AppRepository): ViewModel() {
    val lists = repository.getLists()
    fun deleteList(id: Int) = repository.deleteList(id)
}