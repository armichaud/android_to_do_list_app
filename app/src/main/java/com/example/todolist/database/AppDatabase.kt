package com.example.todolist.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.todolist.models.ListItem
import com.example.todolist.models.ToDoList
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Database(entities = [ToDoList::class, ListItem::class], version = 1)
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
        return Room
            .databaseBuilder(
                applicationContext,
                AppDatabase::class.java, "database"
            )
            // I might go back at some point and change everything to LiveData,
            // but this is fine for the MVP
            .allowMainThreadQueries()
            .build()
    }
}
