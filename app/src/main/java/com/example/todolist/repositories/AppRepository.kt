package com.example.todolist.repositories
import com.example.todolist.database.AppDatabase
import com.example.todolist.viewmodels.ListItem
import com.example.todolist.viewmodels.Status
import com.example.todolist.viewmodels.ToDoList
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AppRepository @Inject constructor(private var db: AppDatabase) {
    private val listDao = db.listDao()
    private val listItemDao = db.listItemDao()

    fun getLists(): Flow<List<ToDoList>> = listDao.getAll()

    fun newList(name: String) {
        listDao.addList(ToDoList(name = name))
    }

    fun getListContents(listId: Int): Flow<List<ListItem>> =
        listItemDao.getAllForList(listId)

    fun addListItem(listItem: ListItem) {
        listItemDao.addListItem(listItem)
    }

    fun deleteListItemById(id: Int) {
        listItemDao.deleteListItem(id)
    }

    fun updateListItemStatus(id: Int, updatedStatus: Status) {
        listItemDao.updateListItemStatus(id, updatedStatus)
    }
}