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
    endDate = confDomainDateFormat.format(Date(endDate * 1000L)),
    categories = listOf(Conference.Category.Android, Conference.Category.CSS)
)

fun ConferenceRemote.toConferenceLocal() = ConferenceLocal(
    name = name,
    url = url,
    cfpUrl = cfpUrl,
    city = city,
    country = country,
    twitter = twitter,
    cfpEndDate = if (!cfpEndDate.isNullOrBlank()) {
        0//(confRemoteDateFormat.parse(cfpEndDate!!.replace('–', '-')).time / 1000).toInt()
    } else null,
    startDate = if (!startDate.isBlank()) {
       0// (confRemoteDateFormat.parse(startDate.replace('–', '-')).time / 1000).toInt()
    } else 0,
    endDate = if (!endDate.isBlank()) {
        0 //(confRemoteDateFormat.parse(endDate.replace('–', '-')).time / 1000).toInt()
    } else 0, //TODO: cant be null
    categories = listOf(Conference.Category.Android.value, Conference.Category.CSS.value)
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
    endDate = (confDomainDateFormat.parse(endDate).time / 1000).toInt(),
    categories =  listOf(Conference.Category.Android.value, Conference.Category.CSS.value)
)