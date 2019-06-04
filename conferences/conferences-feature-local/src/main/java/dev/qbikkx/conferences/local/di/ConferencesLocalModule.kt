package dev.qbikkx.conferences.local.di

import dagger.Binds
import dagger.Module
import dev.qbikkx.conferences.local.ConferencesLocalDataSource
import dev.qbikkx.conferences.local.ConferencesLocalDataSourceImpl

@Module
internal interface ConferencesLocalModule {

    @Binds
    fun bindConferencesLocalDataSource(impl: ConferencesLocalDataSourceImpl): ConferencesLocalDataSource
}