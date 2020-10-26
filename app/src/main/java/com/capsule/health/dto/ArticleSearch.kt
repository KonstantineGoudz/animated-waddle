package com.capsule.health.dto

import com.capsule.health.models.articles.Article
import com.squareup.moshi.Json

data class ArticleSearchResult(
    @field:Json(name = "response") val response: ArticleResponse
)

data class ArticleResponse(
    @field:Json(name = "docs") val docs: List<Article>,
    @field:Json(name = "meta") val meta: ArticleResponseMeta

)

data class ArticleResponseMeta(
    @field:Json(name = "hits") val hits: Int,
    @field:Json(name = "offset") val offset: Int
)
