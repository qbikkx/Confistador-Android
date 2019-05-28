package dev.qbikkx.conferences.remote.source

import dev.qbikkx.conferences.remote.api.ConferencesApi
import javax.inject.Inject

internal class ConferencesRemoteDataSourceImpl @Inject constructor(
    private val conferencesApi: ConferencesApi
) : ConferencesRemoteDataSource {

    override fun getConferences() = conferencesApi.getConferences()
}