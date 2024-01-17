package com.example.todolist.repositories
import com.example.todolist.database.AppDatabase
import com.example.todolist.viewmodels.ListItem
import com.example.todolist.viewmodels.ToDoList
import javax.inject.Inject

class AppRepository @Inject constructor(private var db: AppDatabase) {
    private val listDao = db.listDao()
    private val listItemDao = db.listItemDao()

    fun getLists(): List<ToDoList> = listDao.getAll()

    fun newList(name: String) {
        listDao.addList(name)
    }

    fun getListContents(listId: Int): List<ListItem> =
        listItemDao.getAllForList(listId = listId)
}