package dev.qbikkx.conferences.local.di

import dagger.Module
import dagger.Provides
import dev.qbikkx.conferences.local.ConferencesDatabase

@Module
internal object ConferencesLocalModuleInternal {

    @Provides
    @JvmStatic
    fun provideConferencesDao(db: ConferencesDatabase) = db.getConferencesDao()
}