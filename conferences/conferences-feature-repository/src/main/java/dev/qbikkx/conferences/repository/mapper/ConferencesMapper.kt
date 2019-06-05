package dev.qbikkx.conferences.repository.mapper

import dev.qbikkx.conferences.core.Conference
import dev.qbikkx.conferences.local.ConferenceLocal
import dev.qbikkx.conferences.remote.model.ConferenceRemote
import java.text.SimpleDateFormat
import java.util.*

private val confRemoteDateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

fun ConferenceLocal.toConferenceDomain() = Conference(
    name = name,
    url = url,
    cfpUrl = cfpUrl,
    city = city,
    country = country,
    twitter = twitter,
    cfpEndDate = cfpEndDate?.let { Date(it * 1000L) },
    startDate = Date(startDate * 1000L),
    endDate = Date(endDate * 1000L)
)

fun ConferenceRemote.toConferenceDomain() = Conference(
    name = name,
    url = url,
    cfpUrl = cfpUrl,
    city = city,
    country = country,
    twitter = twitter,
    cfpEndDate = cfpEndDate?.let { confRemoteDateFormat.parse(it) },
    startDate = confRemoteDateFormat.parse(startDate),
    endDate = confRemoteDateFormat.parse(endDate)
)

fun ConferenceRemote.toConferenceLocal() = ConferenceLocal(
    name = name,
    url = url,
    cfpUrl = cfpUrl,
    city = city,
    country = country,
    twitter = twitter,
    cfpEndDate = cfpEndDate?.let { (confRemoteDateFormat.parse(it).time / 1000).toInt() },
    startDate = (confRemoteDateFormat.parse(startDate).time / 1000).toInt(),
    endDate = (confRemoteDateFormat.parse(endDate).time / 1000).toInt()
)

fun Conference.toConferenceLocal() = ConferenceLocal(
    name = name,
    url = url,
    cfpUrl = cfpUrl,
    city = city,
    country = country,
    twitter = twitter,
    cfpEndDate = cfpEndDate?.let { (cfpEndDate!!.time / 1000).toInt() },
    startDate = (startDate.time / 1000).toInt(),
    endDate = (endDate.time / 1000).toInt()
)