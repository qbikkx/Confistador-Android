package dev.qbikkx.conferences.repository

import dev.qbikkx.conferences.core.Conference
import dev.qbikkx.conferences.local.ConferencesLocalDataSource
import dev.qbikkx.conferences.remote.source.ConferencesRemoteDataSource
import dev.qbikkx.conferences.repository.mapper.toConferenceDomain
import dev.qbikkx.conferences.repository.mapper.toConferenceLocal
import dev.qbikkx.core.domain.Result
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

interface ConferencesRepository {

    fun getConferences(): Observable<Result<List<Conference>>>

    fun saveConferences(conferences: List<Conference>): Observable<Result.Status>
}

internal class ConferencesRepositoryImpl @Inject constructor(
    private val local: ConferencesLocalDataSource,
    private val remote: ConferencesRemoteDataSource
) : ConferencesRepository {

    override fun getConferences(): Observable<Result<List<Conference>>> {
        val remoteStream = remote.getConferences()
            .subscribeOn(Schedulers.io())
            .flatMap { conferencesRemote ->
                local.saveConferences(conferencesRemote.map { it.toConferenceLocal() })
                    .toSingleDefault(Result.Status.Success as Result.Status)
            }
            .onErrorReturn { Result.Status.Error(it) }
            .toObservable()
            .startWith(Result.Status.Loading)

        val localStream = local.getConferences()
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.computation())
            .map { conferencesLocal -> conferencesLocal.map { it.toConferenceDomain() } }

        return Observable.combineLatest<List<Conference>, Result.Status, Result<List<Conference>>>(
            localStream,
            remoteStream,
            BiFunction { conferences, status -> Result(data = conferences, status = status) }
        ).startWith(Result<List<Conference>>(data = null, status = Result.Status.Loading))
    }

    override fun saveConferences(conferences: List<Conference>): Observable<Result.Status> {
        return Single.fromCallable { conferences.map { conf -> conf.toConferenceLocal() } }
            .subscribeOn(Schedulers.computation())
            .flatMapCompletable { local.saveConferences(it).subscribeOn(Schedulers.io()) }
            .toSingleDefault(Result.Status.Success as Result.Status)
            .onErrorReturn { Result.Status.Error(it) }
            .toObservable()
            .startWith(Result.Status.Loading)
    }
}