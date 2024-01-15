package com.example.todolist.databases

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Query
import androidx.room.RoomDatabase
import com.example.todolist.viewmodels.ListItem
import com.example.todolist.viewmodels.ToDoList

@Dao
interface ListDao {
    @Query("SELECT * FROM to_do_list")
    fun getAll(): List<ToDoList>
}

@Dao
interface ListItemDao {
    @Query("SELECT * FROM list_item WHERE list_id == :listId ORDER BY status, id")
    fun getAllForList(listId: Int): List<ListItem>
}


@Database(entities = [List::class, ListItem::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun listDao(): ListDao
    abstract fun listItemDao(): ListItemDao
}