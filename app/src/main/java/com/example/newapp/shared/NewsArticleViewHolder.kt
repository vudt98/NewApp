package com.example.newapp.shared

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newapp.R
import com.example.newapp.data.NewsArticle
import com.example.newapp.databinding.ItemNewArticleBinding

class NewsArticleViewHolder(
    private val binding: ItemNewArticleBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(article: NewsArticle) {
        binding.apply {
            Glide.with(itemView)
                .load(article.thumbnail)
                .error(R.drawable.image_placeholder)
                .into(imageView)

            tvTitle.text = article.title ?: ""

            imageViewBookmark.setImageResource(
                when {
                    article.isBookmarked -> R.drawable.ic_bookmark_selected
                    else -> R.drawable.ic_bookmark_unselected
                }
            )
        }
    }
}