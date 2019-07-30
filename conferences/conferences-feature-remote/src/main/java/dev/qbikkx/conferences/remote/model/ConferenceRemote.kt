package dev.qbikkx.conferences.remote.model

data class ConferenceRemote(
    val name: String = "",
    val url: String = "",
    val startDate: String = "",
    val endDate: String = "",
    val city: String = "",
    val country: String = "",
    val cfpUrl: String? = null,
    val cfpStartDate: String? = null,
    val cfpEndDate: String? = null,
    val twitter: String? = null
)