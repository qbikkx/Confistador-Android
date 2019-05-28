package dev.qbikkx.conferences.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ConferenceRemote(
    @Json(name = "name") val name: String,
    @Json(name = "url") val url: String,
    @Json(name = "startDate") val startDate: String,
    @Json(name = "endDate") val endDate: String,
    @Json(name = "city") val city: String,
    @Json(name = "country") val country: String,
    @Json(name = "cfpUrl") val cfpUrl: String? = null,
    @Json(name = "cfpEndDate") val cfpEndDate: String? = null,
    @Json(name = "twitter") val twitter: String? = null
)