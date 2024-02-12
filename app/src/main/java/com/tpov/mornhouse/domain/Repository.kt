package com.tpov.mornhouse.domain

import androidx.lifecycle.LiveData
import com.tpov.mornhouse.data.database.ItemDao
import com.tpov.mornhouse.data.database.models.NumberDetailEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Repository(private val itemDao: ItemDao) {
    val allItems: LiveData<List<NumberDetailEntity>> = itemDao.getAll()

    fun insertItem(item: NumberDetailEntity) = CoroutineScope(Dispatchers.IO).launch {
        itemDao.insert(item)
    }
}
