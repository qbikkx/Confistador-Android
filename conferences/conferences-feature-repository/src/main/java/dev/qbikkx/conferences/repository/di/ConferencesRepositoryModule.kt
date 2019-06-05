package dev.qbikkx.conferences.repository.di

import dagger.Binds
import dagger.Module
import dev.qbikkx.conferences.repository.ConferencesRepository
import dev.qbikkx.conferences.repository.ConferencesRepositoryImpl

@Module
internal interface ConferencesRepositoryModule {

    @Binds
    fun bindConferencesRepository(impl: ConferencesRepositoryImpl): ConferencesRepository
}