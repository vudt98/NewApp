package com.example.newapp.shared

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.newapp.data.NewsArticle
import com.example.newapp.databinding.ItemNewArticleBinding

class NewsArticleListAdapter(
    private val onItemClick : (NewsArticle) -> Unit,
    private val onBookmarkClick: (NewsArticle) -> Unit
) :
    ListAdapter<NewsArticle, NewsArticleViewHolder>(NewsArticleComparator()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsArticleViewHolder {
        val binding =
            ItemNewArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsArticleViewHolder(binding,
            onItemClick = { position ->
                val article = getItem(position)
                if (article != null) {
                    onItemClick(article)
                }
            },
            onBookmarkClick = { position ->
                val article = getItem(position)
                if (article != null){
                    onBookmarkClick(article)
                }
            }
        )
    }

    override fun onBindViewHolder(holder: NewsArticleViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }
}