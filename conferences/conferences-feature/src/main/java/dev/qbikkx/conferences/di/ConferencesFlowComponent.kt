package dev.qbikkx.conferences.di

import dagger.BindsInstance
import dagger.Component
import dev.qbikkx.conferences.ConferencesFlowFragment
import dev.qbikkx.coreui.FlowRouter
import dev.qbikkx.coreui.di.FlowScope
import dev.qbikkx.coreui.di.NavigationFlowInitializer
import dev.qbikkx.coreui.di.NavigationFlowProvider

interface ConferencesFlowProvider {

    fun provideFlowRouter() : FlowRouter

}

@FlowScope
@Component(
    dependencies = [
        NavigationFlowProvider::class
    ],
    modules = [
        ConferencesFlowModule::class
    ]
)
internal interface ConferencesFlowComponent : ConferencesFlowProvider{

    fun inject(fragment: ConferencesFlowFragment)

    @Component.Builder
    interface Builder {

        fun build(): ConferencesFlowComponent

        @BindsInstance
        fun fragment(fragment: ConferencesFlowFragment): Builder

        fun navigationFlowProvider(provider: NavigationFlowProvider): Builder
    }
}

internal object ConferencesFlowInitializer {

    fun init(fragment: ConferencesFlowFragment): ConferencesFlowComponent {
        return DaggerConferencesFlowComponent.builder()
            .fragment(fragment)
            .navigationFlowProvider(NavigationFlowInitializer.init())
            .build()
    }
}