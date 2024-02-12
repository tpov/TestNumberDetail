package com.tpov.mornhouse.data.database

import android.content.Context
import androidx.room.Room
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

object DatabaseBuilder {
    private var instance: AppDatabase? = null

    @OptIn(InternalCoroutinesApi::class)
    fun getInstance(context: Context): AppDatabase {
        if (instance == null) {
            synchronized(AppDatabase::class) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java, "history_number_database"
                ).build()
            }
        }
        return instance!!
    }
}
