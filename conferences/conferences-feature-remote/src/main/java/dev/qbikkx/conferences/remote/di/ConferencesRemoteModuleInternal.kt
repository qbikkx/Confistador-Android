package dev.qbikkx.conferences.remote.di

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dev.qbikkx.conferences.remote.BuildConfig
import dev.qbikkx.conferences.remote.api.ConferencesApi
import dev.qbikkx.conferences.remote.external.CurlLoggingInterceptor
import okhttp3.OkHttpClient
import retrofit2.CallAdapter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
internal object ConferencesRemoteModuleInternal {

    @Singleton
    @JvmStatic
    @Provides
    fun provideObjectMapper(): Moshi {
        return Moshi.Builder().build()
    }

    @Singleton
    @JvmStatic
    @Provides
    fun provideConverterFactory(moshi: Moshi) = MoshiConverterFactory.create(moshi).asLenient()

    @Singleton
    @Provides
    @JvmStatic
    fun provideRxJavaCallAdapterFactory(): CallAdapter.Factory = RxJava2CallAdapterFactory.create()


    @Singleton
    @Provides
    @JvmStatic
    fun provideHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {
            builder.addInterceptor(CurlLoggingInterceptor())
        }

        return builder.build()
    }

    @Singleton
    @Provides
    @JvmStatic
    fun provideRetrofit(
        client: OkHttpClient,
        converterFactory: MoshiConverterFactory,
        rxJavaCallAdapterFactory: CallAdapter.Factory
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