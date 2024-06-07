package com.mvi.data.di


import com.mvi.data.repository.detail.DetailRepositoryImpl
import com.mvi.data.repository.movie.MovieRepositoryImpl
import com.mvi.data.source.detail.DetailRemoteDataSource
import com.mvi.data.source.detail.DetailRemoteDataSourceImpl
import com.mvi.data.source.movie.MovieRemoteDataSource
import com.mvi.data.source.movie.MovieRemoteDataSourceImpl
import com.mvi.domain.repository.detail.DetailRepository
import com.mvi.domain.repository.movie.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideMovieRemoteDataSource(remoteDataSourceImp: MovieRemoteDataSourceImpl): MovieRemoteDataSource

    @Binds
    abstract fun provideDetailDataSource(remoteDataSourceImp: DetailRemoteDataSourceImpl): DetailRemoteDataSource


    @Binds
    @ViewModelScoped
    abstract fun provideMovieRepository(repository: MovieRepositoryImpl): MovieRepository

    @Binds
    @ViewModelScoped
    abstract fun provideDetailRepository(repository: DetailRepositoryImpl): DetailRepository
}