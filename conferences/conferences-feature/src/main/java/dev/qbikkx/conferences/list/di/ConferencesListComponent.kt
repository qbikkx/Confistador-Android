package dev.qbikkx.conferences.list.di

import dagger.BindsInstance
import dagger.Component
import dev.qbikkx.conferences.ConferencesFlowFragment
import dev.qbikkx.conferences.di.ConferencesFlowProvider
import dev.qbikkx.conferences.domain.di.ConferencesDomainInitializer
import dev.qbikkx.conferences.domain.di.ConferencesDomainProvider
import dev.qbikkx.conferences.list.ConferencesListFragment

@Component(
    dependencies = [
        ConferencesFlowProvider::class,
        ConferencesDomainProvider::class
    ], modules = [
        ConferencesListModule::class
    ]
)
internal interface ConferencesListComponent {

    fun inject(fragment: ConferencesListFragment)

    @Component.Builder
    interface Builder {

        fun build(): ConferencesListComponent

        fun conferencesDomainProvider(provider: ConferencesDomainProvider): Builder

        fun conferencesFlowProvider(provider: ConferencesFlowProvider): Builder

        @BindsInstance
        fun fragment(fragment: ConferencesListFragment): Builder
    }
}

internal object ConferencesListInitializer {

    fun init(fragment: ConferencesListFragment): ConferencesListComponent {
        val domain = ConferencesDomainInitializer.init(fragment.activity!!.applicationContext)
        val flow = (fragment.parentFragment as ConferencesFlowFragment).component
        return DaggerConferencesListComponent.builder()
            .fragment(fragment)
            .conferencesDomainProvider(domain)
            .conferencesFlowProvider(flow)
            .build()
    }
}