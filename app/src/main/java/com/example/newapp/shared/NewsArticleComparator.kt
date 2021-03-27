package com.example.newapp.shared

import androidx.recyclerview.widget.DiffUtil
import com.example.newapp.data.NewsArticle

class NewsArticleComparator : DiffUtil.ItemCallback<NewsArticle>() {
    override fun areItemsTheSame(oldItem: NewsArticle, newItem: NewsArticle) = oldItem.url == newItem.url

    override fun areContentsTheSame(oldItem: NewsArticle, newItem: NewsArticle) =
        oldItem == newItem

}