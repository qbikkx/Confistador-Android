package dev.qbikkx.conferences.remote.di

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dev.qbikkx.conferences.remote.BuildConfig
import dev.qbikkx.conferences.remote.api.ConferencesApi
import dev.qbikkx.conferences.remote.model.ConferenceRemote
import dev.qbikkx.conferences.remote.model.ConferenceRemoteJsonAdapter
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
internal object ConferencesRemoteModuleInternal {

    @Singleton
    @JvmStatic
    @Provides
    fun provideObjectMapper(): Moshi {
        var moshi = Moshi.Builder().build()
        moshi = moshi.newBuilder().add(ConferenceRemoteJsonAdapter(moshi)).build()
        val adapter = moshi.adapter(ConferenceRemote::class.java)
        TODO() //return MoshiConverterFactory.create()
    }

    @Singleton
    @JvmStatic
    @Provides
    fun provideConverterFactory(moshi: Moshi) = MoshiConverterFactory.create(moshi)

    @Singleton
    @Provides
    @JvmStatic
    fun provideRxJavaCallAdapterFactory() = RxJavaCallAdapterFactory.create()


    @Singleton
    @Provides
    @JvmStatic
    fun provideHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {
            //builder.addInterceptor()
        }

        return builder.build()
    }

    @Singleton
    @Provides
    @JvmStatic
    fun provideRetrofit(
        client: OkHttpClient,
        converterFactory: MoshiConverterFactory,
        rxJavaCallAdapterFactory: RxJavaCallAdapterFactory
    ) = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_API_URL)
        .addConverterFactory(converterFactory)
        .addCallAdapterFactory(rxJavaCallAdapterFactory)
        .client(client)
        .build()

    @Singleton
    @JvmStatic
    @Provides
    fun provideConferencesApi(retrofit: Retrofit) = retrofit.create(ConferencesApi::class.java)
}