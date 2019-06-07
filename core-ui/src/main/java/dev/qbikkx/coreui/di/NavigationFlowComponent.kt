package dev.qbikkx.coreui.di

import dagger.BindsInstance
import dagger.Component
import dev.qbikkx.coreui.LocalCiceroneHolder
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router

interface NavigationFlowProvider {

    fun provideLocalCiceroneHolder(): LocalCiceroneHolder

    fun provideCicecore(): Cicerone<Router>
}

@Component
internal interface NavigationFlowComponent : NavigationFlowProvider {

    @Component.Builder
    interface Builder {

        fun build(): NavigationFlowComponent

        @BindsInstance
        fun localCiceroneHolder(holder: LocalCiceroneHolder): Builder

        @BindsInstance
        fun cicerone(cicerone: Cicerone<Router>): Builder
    }
}

object NavigationFlowInitializer {

    @Volatile
    private var component: NavigationFlowComponent? = null

    fun init(): NavigationFlowProvider {
        return component ?: synchronized(this) { component ?: create() }
    }

    private fun create(): NavigationFlowComponent {
        component = DaggerNavigationFlowComponent.builder()
            .localCiceroneHolder(LocalCiceroneHolder())
            .cicerone(Cicerone.create())
            .build()
        return component!!
    }
}