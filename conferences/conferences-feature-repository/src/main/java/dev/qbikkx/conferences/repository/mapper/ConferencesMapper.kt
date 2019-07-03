package dev.qbikkx.conferences.repository.mapper

import dev.qbikkx.conferences.core.Conference
import dev.qbikkx.conferences.local.ConferenceLocal
import dev.qbikkx.conferences.remote.model.ConferenceRemote
import java.text.SimpleDateFormat
import java.util.*

private val confRemoteDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
private val confDomainDateFormat = SimpleDateFormat("dd MMM, yyyy", Locale.US)

fun ConferenceLocal.toConferenceDomain() = Conference(
    name = name,
    url = url,
    cfpUrl = cfpUrl,
    city = city,
    country = country,
    twitter = twitter,
    cfpEndDate = cfpEndDate?.let { confDomainDateFormat.format(Date(it * 1000L)) },
    startDate = confDomainDateFormat.format(Date(startDate * 1000L)),
    endDate = confDomainDateFormat.format(Date(endDate * 1000L))
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
    cfpEndDate = cfpEndDate?.let { (confDomainDateFormat.parse(it).time / 1000).toInt() },
    startDate = (confDomainDateFormat.parse(startDate).time / 1000).toInt(),
    endDate = (confDomainDateFormat.parse(endDate).time / 1000).toInt()
)