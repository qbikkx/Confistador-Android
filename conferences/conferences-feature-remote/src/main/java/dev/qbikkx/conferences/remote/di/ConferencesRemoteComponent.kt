package dev.qbikkx.conferences.remote.di

import dagger.Component
import dev.qbikkx.conferences.remote.source.ConferencesRemoteDataSource
import javax.inject.Singleton

interface ConferencesRemoteProvider {

    fun provideConferencesDataSource(): ConferencesRemoteDataSource
}

@Singleton
@Component(modules = [ConferencesRemoteModuleInternal::class, ConferencesRemoteModule::class])
internal interface ConferencesRemoteComponent : ConferencesRemoteProvider

object ConferencesRemoteInitializer {

    @Volatile
    private var component: ConferencesRemoteComponent? = null

    fun init(): ConferencesRemoteProvider {
        return component ?: synchronized(this) { component ?: create() }
    }

    private fun create(): ConferencesRemoteComponent {
        component = DaggerConferencesRemoteComponent.builder().build()
        return component!!
    }
}