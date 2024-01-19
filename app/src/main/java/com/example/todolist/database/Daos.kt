package com.example.todolist.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.todolist.models.ListItem
import com.example.todolist.models.ToDoList
import com.example.todolist.utils.Status
import kotlinx.coroutines.flow.Flow

@Dao
interface ListDao {
    @Query("SELECT * FROM to_do_list")
    fun getAll(): Flow<List<ToDoList>>

    @Insert
    fun addList(list: ToDoList): Long

    @Query("SELECT * FROM to_do_list WHERE id = :id")
    fun getList(id: Long): ToDoList

    @Query("DELETE FROM to_do_list WHERE id = :id")
    fun deleteList(id: Int)
}

@Dao
interface ListItemDao {
    @Query("SELECT * FROM list_item WHERE list_id == :listId ORDER BY status DESC, id")
    fun getAllForList(listId: Int): Flow<List<ListItem>>

    @Insert
    fun addListItem(listItem: ListItem)

    @Query("DELETE FROM list_item WHERE id == :id")
    fun deleteListItem(id: Int)

    @Query("DELETE FROM list_item WHERE list_id = :listId")
    fun deleteListContents(listId: Int)

    @Query("UPDATE list_item SET status = :status WHERE id = :id")
    fun updateListItemStatus(id: Int, status: Status)
}