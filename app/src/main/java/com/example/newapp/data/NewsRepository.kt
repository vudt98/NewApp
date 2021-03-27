package com.example.newapp.data

import androidx.room.withTransaction
import com.example.newapp.api.NewsApi
import com.example.newapp.utils.Resource
import com.example.newapp.utils.networkBoundResource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val newsApi: NewsApi,
    private val newsArticleDb: NewsArticleDatabase
) {
    private val newArticleDao = newsArticleDb.newArticleDao()

    fun getBreakingNews(): Flow<Resource<List<NewsArticle>>> =
        networkBoundResource(
            query = {
                newArticleDao.getAllBreakingNewsArticle()
            },
            fetch = {
                val response = newsApi.getBreakingNews()
                response.articles
            },
            saveFetchResult = { severBreakingNewsArticles ->
                val breakingNewsArticle =
                    severBreakingNewsArticles.map { severBreakingNewsArticle ->
                        NewsArticle(
                            title = severBreakingNewsArticle.title,
                            url = severBreakingNewsArticle.url,
                            thumbnail = severBreakingNewsArticle.urlToImage,
                            isBookmarked = false
                        )
                    }
                val breakingNews = breakingNewsArticle.map { article ->
                    BreakingNews(article.url)
                }

                newsArticleDb.withTransaction {
                    newArticleDao.deleteAllBreakingNews()
                    newArticleDao.insertArticle(breakingNewsArticle)
                    newArticleDao.insertBreakingNews(breakingNews)
                }
            }
        )
}