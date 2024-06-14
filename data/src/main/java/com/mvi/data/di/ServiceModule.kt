package com.mvi.data.di

import com.mvi.data.api.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Module that holds Network related classes
 */

@Module
@InstallIn(ViewModelComponent::class)
object ServiceModule {

    /**
     * Provides [ApiService] instance
     */
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    internal fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

}