package com.example.todolist.database

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.todolist.viewmodels.ListItem
import com.example.todolist.viewmodels.ToDoList
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Database(entities = [List::class, ListItem::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun listDao(): ListDao
    abstract fun listItemDao(): ListItemDao
}

@InstallIn(SingletonComponent::class)
@Module
object DataBaseModule {
    @Provides
    fun provideDatabase(
        @ApplicationContext applicationContext: Context
    ): AppDatabase  {
        return Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database"
        ).build()
    }
}

@Dao
interface ListItemDao {
    @Query("SELECT * FROM list_item WHERE list_id == :listId ORDER BY status DESC, id")
    fun getAllForList(listId: Int): List<ListItem>
}

@Dao
interface ListDao {
    @Query("SELECT * FROM to_do_list")
    fun getAll(): List<ToDoList>
}