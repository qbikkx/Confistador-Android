package dev.qbikkx.conferences.remote.api

import dev.qbikkx.conferences.remote.model.ConferenceRemote
import io.reactivex.Single
import retrofit2.http.GET

internal interface ConferencesApi {

    @GET("conferences/2019/android.json")
    fun getConferences(): Single<List<ConferenceRemote>>
}