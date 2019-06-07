package dev.qbikkx.conferences.di

import dagger.Module
import dagger.Provides
import dev.qbikkx.conferences.ConferencesFlowFragment
import dev.qbikkx.coreui.FlowRouter
import dev.qbikkx.coreui.LocalCiceroneHolder
import dev.qbikkx.coreui.di.FlowScope
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router

@Module
internal object ConferencesFlowModule {

    @JvmStatic
    @Provides
    @FlowScope
    fun provideFlowCicerone(
        fragment: ConferencesFlowFragment,
        parentCicerone: Cicerone<Router>,
        localCiceroneHolder: LocalCiceroneHolder
    ): Cicerone<FlowRouter> {
        return localCiceroneHolder.getCicerone(fragment.flowKey, parentCicerone.router)
    }

    @JvmStatic
    @Provides
    @FlowScope
    fun provideFlowNavHolder(cicerone: Cicerone<FlowRouter>): NavigatorHolder {
        return cicerone.navigatorHolder
    }

    @JvmStatic
    @Provides
    @FlowScope
    fun provideFlowRouter(cicerone: Cicerone<FlowRouter>): FlowRouter {
        return cicerone.router
    }

    @JvmStatic
    @Provides
    fun parentRouter(cicerone: Cicerone<Router>): Router = cicerone.router
}