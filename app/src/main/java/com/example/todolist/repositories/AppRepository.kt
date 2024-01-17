package com.example.todolist.repositories
import com.example.todolist.database.AppDatabase
import com.example.todolist.viewmodels.ListItem
import com.example.todolist.viewmodels.ToDoList
import javax.inject.Inject

class AppRepository @Inject constructor(private var db: AppDatabase) {
    private val listDao = db.listDao()
    private val listItemDao = db.listItemDao()

    fun getLists(): List<ToDoList> = listOf(ToDoList(1, "First List"), ToDoList(2, "Second List"))
        // listDao.getAll()

    fun getListContents(listId: Int): List<ListItem> =
        listItemDao.getAllForList(listId = listId)
}