package dev.qbikkx.conferences.repository.di

import android.content.Context
import dagger.Component
import dev.qbikkx.conferences.local.di.ConferencesLocalInitializer
import dev.qbikkx.conferences.local.di.ConferencesLocalProvider
import dev.qbikkx.conferences.remote.di.ConferencesRemoteInitializer
import dev.qbikkx.conferences.remote.di.ConferencesRemoteProvider
import dev.qbikkx.conferences.repository.ConferencesRepository

interface ConferencesRepositoryProvider {

    fun provideConferencesRepository(): ConferencesRepository
}

@Component(
    dependencies = [
        ConferencesLocalProvider::class,
        ConferencesRemoteProvider::class
    ],
    modules = [ConferencesRepositoryModule::class]
)
internal interface ConferencesRepositoryComponent : ConferencesRepositoryProvider {

    @Component.Builder
    interface Builder {

        fun build(): ConferencesRepositoryComponent

        fun conferencesLocalProvider(conferencesLocalProvider: ConferencesLocalProvider): Builder

        fun conferencesRemoteProvider(conferencesRemoteProvider: ConferencesRemoteProvider): Builder
    }
}

object ConferencesRepositoryInitializer {

    @Volatile
    private var component: ConferencesRepositoryComponent? = null

    fun init(context: Context): ConferencesRepositoryProvider {
        return component ?: synchronized(this) { component ?: create(context) }
    }

    private fun create(context: Context): ConferencesRepositoryComponent {
        val local = ConferencesLocalInitializer.init(context)
        val remote = ConferencesRemoteInitializer.init()
        component = DaggerConferencesRepositoryComponent.builder()
            .conferencesLocalProvider(local)
            .conferencesRemoteProvider(remote)
            .build()
        return component!!
    }
}