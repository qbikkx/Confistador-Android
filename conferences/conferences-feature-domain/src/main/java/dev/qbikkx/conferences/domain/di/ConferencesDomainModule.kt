package dev.qbikkx.conferences.domain.di

import dagger.Module
import dagger.Provides
import dev.qbikkx.conferences.domain.GetConferencesUseCase
import dev.qbikkx.conferences.repository.ConferencesRepository

@Module
internal object ConferencesDomainModule {

    @JvmStatic
    @Provides
    fun provideGetConferencesUseCase(conferencesRepository: ConferencesRepository) =
        GetConferencesUseCase(conferencesRepository)
}