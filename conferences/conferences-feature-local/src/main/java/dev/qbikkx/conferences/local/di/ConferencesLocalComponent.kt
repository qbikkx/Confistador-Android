package dev.qbikkx.conferences.local.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import dev.qbikkx.conferences.local.ConferencesDatabase
import dev.qbikkx.conferences.local.ConferencesLocalDataSource

interface ConferencesLocalProvider {

    fun provideConferencesLocalDataSource(): ConferencesLocalDataSource
}

@Component(modules = [ConferencesLocalModule::class, ConferencesLocalModuleInternal::class])
internal interface ConferencesLocalComponent : ConferencesLocalProvider {

    @Component.Builder
    interface Builder {

        fun build(): ConferencesLocalComponent

        @BindsInstance
        fun bindDatabase(db: ConferencesDatabase): Builder
    }
}

object ConferencesLocalInitializer {
    @Volatile
    private var component: ConferencesLocalComponent? = null

    fun init(context: Context): ConferencesLocalProvider {
        return component ?: synchronized(this) { component ?: create(context) }
    }

    private fun create(context: Context): ConferencesLocalComponent {
        val db = ConferencesDatabase.getInstance(context)
        component = DaggerConferencesLocalComponent.builder()
            .bindDatabase(db)
            .build()
        return component!!
    }
}