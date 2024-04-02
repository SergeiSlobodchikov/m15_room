package com.example.room.ui.main

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "words")
data class Word(
    @PrimaryKey
    @ColumnInfo(name = "value")
    val value: String,
    @ColumnInfo(name = "repetition")
    var repetition: Int
)
