package com.mvi.data.repository.movie

import com.mvi.base.BaseRepository
import com.mvi.base.Resource
import com.mvi.data.mapper.movie.MovieListDataDomainMapper
import com.mvi.data.source.movie.MovieRemoteDataSource
import com.mvi.domain.entity.movie.MovieEntityModel
import com.mvi.domain.repository.movie.MovieRepository
import javax.inject.Inject

/**
 * Implementation class of [MovieRepository]
 */
class MovieRepositoryImpl @Inject constructor(
    private val movieRemoteDataSource: MovieRemoteDataSource,
    private val movieMapper: MovieListDataDomainMapper,
) : BaseRepository(), MovieRepository {

    override suspend fun fetchMovieList(): Resource<MovieEntityModel> {
        return fetchAPiData(
            { movieRemoteDataSource.fetchMovieList() }, movieMapper
        )

    }
}