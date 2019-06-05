package dev.qbikkx.conferences.domain

import dev.qbikkx.conferences.core.Conference
import dev.qbikkx.conferences.repository.ConferencesRepository
import dev.qbikkx.core.domain.Result
import io.reactivex.Observable

class GetConferencesUseCase(
    private val conferencesRepository: ConferencesRepository
) {

    fun execute(): Observable<Result<List<Conference>>> {
        return conferencesRepository.getConferences()
    }
}