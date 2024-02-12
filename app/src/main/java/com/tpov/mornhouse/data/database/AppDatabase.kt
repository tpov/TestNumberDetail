package com.tpov.mornhouse.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tpov.mornhouse.data.database.models.NumberDetailEntity

@Database(entities = [NumberDetailEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun itemDao(): ItemDao
}
