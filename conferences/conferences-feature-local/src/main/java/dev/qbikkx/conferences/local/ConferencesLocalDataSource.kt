package dev.qbikkx.conferences.local

import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

interface ConferencesLocalDataSource {

    fun getConferences(): Observable<List<ConferenceLocal>>

    fun saveConferences(conferences: List<ConferenceLocal>): Completable
}

internal class ConferencesLocalDataSourceImpl @Inject constructor(
    private val conferencesDao: ConferencesDao
) : ConferencesLocalDataSource {

    override fun getConferences() = conferencesDao.getConferences()

    override fun saveConferences(conferences: List<ConferenceLocal>) = conferencesDao.saveConferences(conferences)
}