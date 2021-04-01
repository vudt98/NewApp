package com.example.newapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news_articles")
data class NewsArticle(
    val title: String?,
    @PrimaryKey
    var url: String,
    val thumbnail: String?,
    val isBookmarked: Boolean,
    val updatedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "breaking_news")
data class BreakingNews(
    val articleUrl: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)