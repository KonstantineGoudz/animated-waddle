package com.capsule.health.models.concept

import com.squareup.moshi.Json
import java.io.Serializable

data class Concept(
    @field:Json(name = "concept_id") val conceptId: Long,
    @field:Json(name = "concept_name") val conceptName: String,
    @field:Json(name = "concept_status") val conceptStatus: String,
    @field:Json(name = "concept_type") val conceptType: String
) : Serializable