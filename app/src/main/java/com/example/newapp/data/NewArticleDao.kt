package com.example.newapp.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao
interface NewArticleDao {
    @Query("SELECT * FROM breaking_news INNER JOIN news_articles ON articleUrl = url")
    fun getAllBreakingNewsArticle(): Flow<List<NewsArticle>>

    @Query("SELECT * FROM news_articles WHERE isBookmarked = 1")
    fun getAllBookmarkedArticle(): Flow<List<NewsArticle>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(article: List<NewsArticle>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBreakingNews(breakingNews: List<BreakingNews>)

    @Update
    suspend fun updateArticle(article: NewsArticle)

    @Query("UPDATE news_articles SET isBookmarked = 0")
    suspend fun resetAllBookmarks()

    @Query("DELETE FROM breaking_news")
    suspend fun deleteAllBreakingNews()

    @Query("DELETE FROM news_articles WHERE updatedAt < :timestampInMillis AND isBookmarked = 0")
    suspend fun deleteNonBookmarkedArticlesOlderThan(timestampInMillis: Long)
}