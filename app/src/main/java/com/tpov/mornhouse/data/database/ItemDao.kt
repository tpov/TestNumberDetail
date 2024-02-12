package com.tpov.mornhouse.data.database

import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.Query
import com.tpov.mornhouse.data.database.models.NumberDetailEntity

@androidx.room.Dao
interface ItemDao {
    @Query("SELECT * FROM items")
    fun getAll(): LiveData<List<NumberDetailEntity>>

    @Insert
    fun insert(item: NumberDetailEntity)
}
