package com.capsule.health.adapters.articles

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.capsule.health.databinding.ListItemArticleBinding
import com.capsule.health.models.articles.Article

class ArticleAdapter(val onItemSelected: (Article) -> Unit) :
    PagedListAdapter<Article, ArticleAdapter.ArticleViewHolder>(Companion) {

    companion object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.id == newItem.id
        }
    }

    class ArticleViewHolder(
        val conceptBinding: ListItemArticleBinding
    ) :
        RecyclerView.ViewHolder(conceptBinding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ArticleViewHolder {
        ListItemArticleBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ).let {
            return ArticleViewHolder(it)
        }
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.conceptBinding.apply {
            article = getItem(position)
            article?.let { article ->
                root.setOnClickListener {
                    onItemSelected(article)
                }
            }
            executePendingBindings()
        }
    }

}
