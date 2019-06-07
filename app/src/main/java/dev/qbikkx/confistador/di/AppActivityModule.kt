package dev.qbikkx.confistador.di

import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router

@Module
internal object AppActivityModule {

    @JvmStatic
    @Provides
    fun provideRouter(cicerone: Cicerone<Router>) = cicerone.router

    @JvmStatic
    @Provides
    fun provideNavHolder(cicerone: Cicerone<Router>) = cicerone.navigatorHolder
}