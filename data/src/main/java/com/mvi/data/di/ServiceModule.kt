package com.mvi.data.di

import com.mvi.data.api.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Module that holds Network related classes
 */

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    /**
     * Provides [ApiService] instance
     */
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

}