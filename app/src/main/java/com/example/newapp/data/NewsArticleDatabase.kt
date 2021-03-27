package com.example.newapp.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [NewsArticle::class,BreakingNews::class], version = 1)
abstract class NewsArticleDatabase : RoomDatabase() {

    abstract fun newArticleDao() : NewArticleDao
}