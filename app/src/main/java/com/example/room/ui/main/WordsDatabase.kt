package com.example.room.ui.main

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Word::class], version = 1)
abstract class WordsDatabase : RoomDatabase() {
    abstract fun wordsDao(): WordsDao
}