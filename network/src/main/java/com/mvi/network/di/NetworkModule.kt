package com.mvi.network.di

import com.mvi.network.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Module that holds Network related classes
 */

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    /**
     * Provides [HttpLoggingInterceptor] instance
     */

    @Provides
    fun providesHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        BuildConfig.DEBUG.let {
            if (it) {
                httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            } else {
                httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE)
            }
        }
        return httpLoggingInterceptor
    }

    /**
     * Provides [OkHttpClient] instance
     */

    @Provides
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .retryOnConnectionFailure(true).build()
    }

    /**
     * Provides [Retrofit] instance
     */
    @Provides
    fun provideRetrofitTest(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }


}