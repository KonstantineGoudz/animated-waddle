package com.capsule.health.dto

import com.capsule.health.models.concept.Concept
import com.squareup.moshi.Json

data class ConceptSearchResult(
    @field:Json(name = "status") val status: String,
    @field:Json(name = "num_results") val num_results: Int,
    @field:Json(name = "results") val results: List<Concept>
)