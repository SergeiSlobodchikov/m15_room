package com.example.room.ui.main

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow

class MainViewModel(
    private val repository: Repository
) : ViewModel() {


    fun getList(): Flow<List<Word>> {
        return repository.wordsDb.getList()
    }

    fun clearDb() {
        repository.wordsDb.getWords().forEach { word ->
            repository.wordsDb.removeWord(word)
        }
    }

    fun insertOrUpdate(word: Word) {
        repository.wordsDb.insertOrUpdate(word)
    }
}