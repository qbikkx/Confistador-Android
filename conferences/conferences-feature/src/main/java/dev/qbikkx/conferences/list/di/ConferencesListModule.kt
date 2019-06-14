package dev.qbikkx.conferences.list.di

import androidx.lifecycle.ViewModelProviders
import dagger.Module
import dagger.Provides
import dev.qbikkx.conferences.domain.GetConferencesUseCase
import dev.qbikkx.conferences.list.ConferencesFeatureStore
import dev.qbikkx.conferences.list.ConferencesListElmContainer
import dev.qbikkx.conferences.list.ConferencesListFragment

@Module
internal object ConferencesListModule {

    @Provides
    @JvmStatic
    fun provideElmStore(getConferencesUseCase: GetConferencesUseCase) = ConferencesFeatureStore(getConferencesUseCase)

    @Provides
    @JvmStatic
    fun provideElmContainer(fragment: ConferencesListFragment,
                            factory: ConferencesListElmContainer.Factory) =
        ViewModelProviders.of(fragment, factory).get(ConferencesListElmContainer::class.java)
}