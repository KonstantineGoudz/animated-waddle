package com.capsule.health.binding

import android.net.Uri
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.capsule.health.models.articles.ArticleMultiMedia
import com.capsule.health.models.articles.sortBySize
import com.capsule.health.models.net.LoadingState
import com.squareup.picasso.Picasso

@BindingAdapter(value = ["adapter"])
fun RecyclerView.bindAdapter(adapter: RecyclerView.Adapter<*>?) {
    this.adapter = adapter
}

@BindingAdapter(value = ["multimedia"])
fun ImageView.multimedia(articleMultimedia: List<ArticleMultiMedia>) {
    if (!articleMultimedia.isNullOrEmpty()) {
        articleMultimedia.let { list ->
            list.sortBySize()
            list.first().let { multimedia ->
                val uri = "https://static01.nyt.com/${multimedia.url}"
                Picasso.get()
                    .load(Uri.parse(uri))
                    .into(this)
            }
        }
    } else {
        this.setImageDrawable(null)
    }
}

@BindingAdapter(value = ["loadingState"])
fun View.progress(loadingState: LoadingState?) {
    loadingState?.let {
        when (it) {
            LoadingState.LOADING -> this.visibility = VISIBLE
            LoadingState.COMPLETE -> this.visibility = GONE
            LoadingState.ERROR -> this.visibility = GONE
        }
    }
}