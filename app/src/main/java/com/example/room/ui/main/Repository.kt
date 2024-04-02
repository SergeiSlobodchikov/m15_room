package com.example.room.ui.main

import android.content.Context

class Repository(context: Context?) {
    val wordsDb = (context?.applicationContext as App).db.wordsDao()
}