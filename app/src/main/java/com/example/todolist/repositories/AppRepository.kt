package com.example.todolist.repositories

import android.content.Context
import android.content.ContextWrapper
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.todolist.viewmodels.ListItem
import com.example.todolist.viewmodels.ToDoList
import javax.inject.Inject

@Dao
interface ListDao {
    @Query("SELECT * FROM to_do_list")
    fun getAll(): List<ToDoList>
}

@Dao
interface ListItemDao {
    @Query("SELECT * FROM list_item WHERE list_id == :listId ORDER BY status DESC, id")
    fun getAllForList(listId: Int): List<ListItem>
}


@Database(entities = [List::class, ListItem::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun listDao(): ListDao
    abstract fun listItemDao(): ListItemDao
}


class AppRepository @Inject constructor() {
    private lateinit var db: AppDatabase
    private val listDao = db.listDao()
    private val listItemDao = db.listItemDao()

    fun initDb(applicationContext: Context) {
        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database"
        ).build()
    }

    fun getLists(): List<ToDoList> = listOf(ToDoList(1, "First List"), ToDoList(2, "Second List"))
        // listDao.getAll()

    fun getListContents(listId: Int): List<ListItem> =
        listItemDao.getAllForList(listId = listId)
}