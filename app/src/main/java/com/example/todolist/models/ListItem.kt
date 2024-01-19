package com.example.todolist.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.todolist.utils.Status

@Entity(
    tableName = "list_item",
    foreignKeys = [ForeignKey(
        entity = ListItem::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("list_id")
    )]
)
data class ListItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val label: String,
    var status: Status = Status.Todo,
    @ColumnInfo(name = "list_id") val listId: Int
)