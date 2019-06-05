package dev.qbikkx.conferences.domain.di

import android.content.Context
import dagger.Component
import dev.qbikkx.conferences.domain.GetConferencesUseCase
import dev.qbikkx.conferences.repository.di.ConferencesRepositoryInitializer
import dev.qbikkx.conferences.repository.di.ConferencesRepositoryProvider

interface ConferencesDomainProvider {

    fun provideGetConferencesUseCase(): GetConferencesUseCase
}

@Component(dependencies = [ConferencesRepositoryProvider::class], modules = [ConferencesDomainModule::class])
internal interface ConferencesDomainComponent  : ConferencesDomainProvider

object ConferencesDomainInitializer {

    @Volatile
    private var component: ConferencesDomainComponent? = null

    fun init(context: Context): ConferencesDomainProvider {
        return component ?: synchronized(this) { component ?: create(context) }
    }

    private fun create(context: Context): ConferencesDomainComponent {
        val repo = ConferencesRepositoryInitializer.init(context)
        component = DaggerConferencesDomainComponent.builder()
            .conferencesRepositoryProvider(repo)
            .build()
        return component!!
    }
}