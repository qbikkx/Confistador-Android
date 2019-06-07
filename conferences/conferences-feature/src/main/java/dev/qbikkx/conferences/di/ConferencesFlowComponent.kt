package dev.qbikkx.conferences.di

import dagger.BindsInstance
import dagger.Component
import dev.qbikkx.conferences.ConferencesFlowFragment
import dev.qbikkx.conferences.domain.di.ConferencesDomainInitializer
import dev.qbikkx.conferences.domain.di.ConferencesDomainProvider
import dev.qbikkx.coreui.di.FlowScope
import dev.qbikkx.coreui.di.NavigationFlowInitializer
import dev.qbikkx.coreui.di.NavigationFlowProvider

@FlowScope
@Component(
    dependencies = [
        ConferencesDomainProvider::class,
        NavigationFlowProvider::class
    ],
    modules = [
        ConferencesFlowModule::class
    ]
)
internal interface ConferencesFlowComponent {

    fun inject(fragment: ConferencesFlowFragment)

    @Component.Builder
    interface Builder {

        fun build(): ConferencesFlowComponent

        @BindsInstance
        fun fragment(fragment: ConferencesFlowFragment): Builder

        fun conferencesDomainProvider(provider: ConferencesDomainProvider): Builder

        fun navigationFlowProvider(provider: NavigationFlowProvider): Builder
    }
}

internal object ConferencesFlowInitializer {

    fun init(fragment: ConferencesFlowFragment): ConferencesFlowComponent {
        return DaggerConferencesFlowComponent.builder()
            .fragment(fragment)
            .conferencesDomainProvider(ConferencesDomainInitializer.init(fragment.activity!!.applicationContext))
            .navigationFlowProvider(NavigationFlowInitializer.init())
            .build()
    }
}