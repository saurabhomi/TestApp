package com.mvi.domain.di


import com.mvi.base.qualifiers.IoDispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers


@Module
@InstallIn(ViewModelComponent::class)
object DispatcherModule {

    @IoDispatcher
    @Provides
    internal fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO


}