package com.tpov.mornhouse.presentation.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.tpov.mornhouse.data.database.models.NumberDetailEntity
import com.tpov.mornhouse.data.remote.NumbersRepository
import com.tpov.mornhouse.domain.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository) : ViewModel() {

    private val repositoryRemote = NumbersRepository()
    val numberTrivia = MutableLiveData<String>()

    fun fetchNumberTrivia(number: Int) = viewModelScope.launch(Dispatchers.IO) {
        numberTrivia.postValue(repositoryRemote.fetchNumberTrivia(number))
    }

    fun getAllItemsLiveData() = repository.allItems

    fun saveDataInDatabase(text: String) {
        repository.insertItem(NumberDetailEntity(null, text))
    }
}

class ViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
