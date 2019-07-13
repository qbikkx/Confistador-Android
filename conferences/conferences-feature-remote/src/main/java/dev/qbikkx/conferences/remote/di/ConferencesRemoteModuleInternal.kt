package dev.qbikkx.conferences.remote.di

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dev.qbikkx.conferences.remote.BuildConfig
import dev.qbikkx.conferences.remote.api.ConferencesApi
import dev.qbikkx.conferences.remote.external.CurlLoggingInterceptor
import okhttp3.OkHttpClient
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
internal object ConferencesRemoteModuleInternal {

    @Singleton
    @JvmStatic
    @Provides
    fun provideObjectMapper() = Gson()


    @Singleton
    @JvmStatic
    @Provides
    fun provideConverterFactory(gson: Gson): Converter.Factory = GsonConverterFactory.create(gson)

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
        converterFactory: Converter.Factory,
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