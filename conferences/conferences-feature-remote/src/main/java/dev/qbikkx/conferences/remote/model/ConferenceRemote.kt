package dev.qbikkx.conferences.remote.model

import com.google.gson.annotations.SerializedName

data class ConferenceRemote(
    @SerializedName(value = "name") val name: String,
    @SerializedName(value = "url") val url: String,
    @SerializedName(value = "startDate") val startDate: String,
    @SerializedName(value = "endDate") val endDate: String,
    @SerializedName(value = "city") val city: String,
    @SerializedName(value = "country") val country: String,
    @SerializedName(value = "cfpUrl") val cfpUrl: String? = null,
    @SerializedName(value = "cfpEndDate") val cfpEndDate: String? = null,
    @SerializedName(value = "twitter") val twitter: String? = null
)