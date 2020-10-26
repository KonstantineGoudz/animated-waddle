package com.capsule.health.repositories

import com.capsule.health.apis.NYTApi
import com.capsule.health.dto.ArticleResponse
import com.capsule.health.models.concept.Concept
import com.capsule.health.models.concept.ConceptType
import javax.inject.Inject

class NYTRepository @Inject constructor(
    private val nytApi: NYTApi,
    private val apiKey: String
) {
    companion object {
        private const val articleSearchFilter =
            "news_desk:(\"Health\" \"Blogs\" \"Editorial\" \"Health & Fitness\" \"Science\" \"Women's Health\")"
    }

    suspend fun getConceptsForTopic(topic: String): List<Concept> {
        return nytApi.searchConcepts(apiKey, ConceptType.TOPIC.value, topic).results
    }

    suspend fun findArticles(searchQuery: String, page: Int = 0): ArticleResponse {
        return nytApi.searchArticles(apiKey, searchQuery, articleSearchFilter, page)
            .response
    }
}