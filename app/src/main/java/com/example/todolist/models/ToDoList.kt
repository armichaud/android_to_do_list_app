package com.example.todolist.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "to_do_list")
data class ToDoList(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String
)