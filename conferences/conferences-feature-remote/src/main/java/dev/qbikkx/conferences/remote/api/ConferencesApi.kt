package dev.qbikkx.conferences.remote.api

import dev.qbikkx.conferences.remote.model.ConferenceRemote
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

internal interface ConferencesApi {

    @GET("conferences/{year}/{category}.json")
    fun getConferences(
        @Path("year") year: String,
        @Path("category") category: String
    ): Single<List<ConferenceRemote>>
}