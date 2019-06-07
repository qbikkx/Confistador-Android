package dev.qbikkx.confistador.di

import dagger.Component
import dev.qbikkx.confistador.AppActivity
import dev.qbikkx.coreui.di.NavigationFlowInitializer
import dev.qbikkx.coreui.di.NavigationFlowProvider

@Component(dependencies = [NavigationFlowProvider::class], modules = [AppActivityModule::class])
internal interface AppActivityComponent {

    fun inject(activity: AppActivity)
}

internal object AppActivityInitializer {

    fun init(): AppActivityComponent {
        return DaggerAppActivityComponent.builder()
            .navigationFlowProvider(NavigationFlowInitializer.init())
            .build()
    }
}