package com.example.room.ui.main

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface WordsDao {

    @Query("SELECT * FROM words LIMIT 5")
    fun getList(): Flow<List<Word>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(word: Word): Long

    @Query("UPDATE words SET repetition = repetition + 1 WHERE value = :word")
    fun update(word: String)

    @Transaction
    fun insertOrUpdate(word: Word) {
        val insertResult = insert(word)
        if (insertResult == -1L) update(word.value)
    }

    @Delete
    fun removeWord(word: Word)

    @Query("SELECT * FROM words")
    fun getWords(): List<Word>
}