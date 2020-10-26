package com.capsule.health.fragment.news

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.capsule.health.adapters.articles.ArticleDataFactory
import com.capsule.health.models.concept.Concept
import com.capsule.health.models.net.LoadingState
import com.capsule.health.models.net.NetworkResource
import com.capsule.health.models.net.load
import com.capsule.health.repositories.NYTRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll

class NewsViewModel @ViewModelInject constructor(private val nytRepository: NYTRepository) :
    ViewModel() {

    fun selectConcept(concept: Concept) {
        articleDataFactory.query = concept.conceptName
        this.concept.postValue(concept)
    }

    val concepts = MutableLiveData<NetworkResource<List<Concept>>>()
    val concept = MutableLiveData<Concept>()

    val progress = MutableLiveData<LoadingState>()

    private val articleDataFactory = ArticleDataFactory(nytRepository, progress)

    init {
        concepts.load(viewModelScope) { scope ->
            listOf(
                "health", "diet", "exercise", "prevention"
            ).map { term ->
                scope.async {
                    try {
                        nytRepository.getConceptsForTopic(term)
                    } catch (e: Throwable) {
                        concepts.postValue(NetworkResource.error(e.message, e))
                        listOf<Concept>()
                    }
                }
            }.awaitAll()
                .flatten()
                .also { concepts ->
                    concepts.first().let { first ->
                        articleDataFactory.query = first.conceptName
                        concept.postValue(first)
                    }
                }
        }
    }

    val articles = LivePagedListBuilder(
        articleDataFactory,
        PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(10)
            .setPageSize(10)
            .build()
    ).build()


}