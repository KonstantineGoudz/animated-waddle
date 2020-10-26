package com.capsule.health.apis

import com.capsule.health.dto.ArticleSearchResult
import com.capsule.health.dto.ConceptSearchResult
import retrofit2.http.GET
import retrofit2.http.Query

interface NYTApi {
    @GET("semantic/v2/concept/search.json")
    suspend fun searchConcepts(
        @Query("api-key") apiKey: String,
        @Query("concept_type") conceptType: String,
        @Query("query") query: String
    ) : ConceptSearchResult

    @GET("search/v2/articlesearch.json")
    suspend fun searchArticles(
        @Query("api-key") apiKey: String,
        @Query("q") query: String,
        @Query("fq") filter: String,
        @Query("page") page: Int
    ) : ArticleSearchResult
}