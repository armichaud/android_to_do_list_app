package com.example.todolist.repositories
import com.example.todolist.database.AppDatabase
import com.example.todolist.models.ListItem
import com.example.todolist.utils.Status
import com.example.todolist.models.ToDoList
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AppRepository @Inject constructor(private var db: AppDatabase) {
    private val listDao = db.listDao()
    private val listItemDao = db.listItemDao()

    fun getLists(): Flow<List<ToDoList>> = listDao.getAll()

    fun deleteList(id: Int) {
        listItemDao.deleteListContents(id)
        listDao.deleteList(id)
    }

    fun newList(name: String): Long = listDao.addList(ToDoList(name = name))

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