package dev.qbikkx.conferences.remote.source

import dev.qbikkx.conferences.remote.model.ConferenceRemote
import io.reactivex.Single


interface ConferencesRemoteDataSource {

    fun getConferences(): Single<List<ConferenceRemote>>
}