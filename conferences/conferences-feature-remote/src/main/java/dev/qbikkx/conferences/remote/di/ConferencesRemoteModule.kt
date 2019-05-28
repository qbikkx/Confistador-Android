package dev.qbikkx.conferences.remote.di

import dagger.Binds
import dagger.Module
import dev.qbikkx.conferences.remote.source.ConferencesRemoteDataSource
import dev.qbikkx.conferences.remote.source.ConferencesRemoteDataSourceImpl

@Module
internal interface ConferencesRemoteModule {

    @Binds
    fun bindConferencesRemoteDataSource(impl: ConferencesRemoteDataSourceImpl): ConferencesRemoteDataSource
}