package com.capsule.health.models.articles

import com.squareup.moshi.Json

data class Article(
    @field:Json(name = "abstract") val abstract: String,
    @field:Json(name = "snippet") val snippet: String,
    @field:Json(name = "lead_paragraph") val leadParagraph: String,
    @field:Json(name = "_id") val id: String,
    @field:Json(name = "web_url") val url: String,
    @field:Json(name = "headline") val headline: ArticleHeadline,
    @field:Json(name = "multimedia") val multimedia: List<ArticleMultiMedia>
)

data class ArticleHeadline(
    @field:Json(name = "main") val main: String
)

data class ArticleMultiMedia(
    @field:Json(name = "url") val url: String,
    @field:Json(name = "width") val width: Int,
    @field:Json(name = "height") val height: Int
)

fun List<ArticleMultiMedia>.sortBySize() {
    this.sortedWith(
        Comparator { o1, o2 ->
            o1.width.compareTo(o2.width)
        }
    )
}
