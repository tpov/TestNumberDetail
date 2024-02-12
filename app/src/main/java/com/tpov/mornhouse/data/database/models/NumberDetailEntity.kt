package com.tpov.mornhouse.data.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items")
data class NumberDetailEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val name: String
)