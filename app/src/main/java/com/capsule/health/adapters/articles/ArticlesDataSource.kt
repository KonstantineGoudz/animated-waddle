package com.capsule.health.adapters.articles

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.capsule.health.models.articles.Article
import com.capsule.health.models.net.LoadingState
import com.capsule.health.repositories.NYTRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ArticlesDataSource(
    private val nytRepository: NYTRepository,
    private val progress: MutableLiveData<LoadingState>,
    private var query: String?
) :
    PageKeyedDataSource<Int, Article>() {
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Article>
    ) {
        query?.let { q ->
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    progress.postValue(LoadingState.LOADING)
                    nytRepository.findArticles(q).let {
                        val nextPage = if ((it.meta.offset + it.docs.size) < it.meta.hits) {
                            1
                        } else {
                            null
                        }
                        callback.onResult(it.docs, null, nextPage)
                        progress.postValue(LoadingState.COMPLETE)
                    }
                } catch (e: Throwable) {
                    progress.postValue(LoadingState.ERROR)
                }
            }
        } ?: run {
            callback.onResult(listOf(), null, null)
            progress.postValue(LoadingState.COMPLETE)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Article>) {

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Article>) {
        query?.let { q ->
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    progress.postValue(LoadingState.LOADING)
                    nytRepository.findArticles(q, params.key).let {
                        val nextPage = if ((it.meta.offset + it.docs.size) < it.meta.hits) {
                            params.key + 1
                        } else {
                            null
                        }
                        callback.onResult(it.docs, nextPage)
                        progress.postValue(LoadingState.COMPLETE)
                    }
                } catch (e: Throwable) {
                    callback.onResult(listOf(), params.key)
                    progress.postValue(LoadingState.ERROR)
                }
            }
        } ?: run {
            callback.onResult(listOf(), null)
            progress.postValue(LoadingState.COMPLETE)
        }
    }
}

class ArticleDataFactory(
    private val nytRepository: NYTRepository,
    private val progress: MutableLiveData<LoadingState>
) :
    DataSource.Factory<Int, Article>() {

    var query: String? = null
        set(value) {
            field = value
            articlesDataSource.invalidate()
        }

    private lateinit var articlesDataSource: ArticlesDataSource

    override fun create(): DataSource<Int, Article> {
        articlesDataSource = ArticlesDataSource(nytRepository, progress, query)
        return articlesDataSource
    }
}