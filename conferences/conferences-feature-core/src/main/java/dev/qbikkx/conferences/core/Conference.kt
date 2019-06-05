package dev.qbikkx.conferences.core

import java.util.*

data class Conference(
    val name: String,
    val url: String,
    val startDate: Date,
    val endDate: Date,
    val city: String,
    val country: String,
    val cfpUrl: String? = null,
    val cfpEndDate: Date? = null,
    val twitter: String? = null
)