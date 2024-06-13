package com.mvi.data.repository.movie

import com.mvi.common.Resource
import com.mvi.data.api.ApiService
import com.mvi.data.mapper.movie.MovieListDataDomainMapper
import com.mvi.domain.model.movie.MovieDomainModel
import com.mvi.domain.repository.movie.MovieRepository
import com.mvi.network.base.BaseRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

/**
 * Implementation class of [MovieRepository]
 */
class MovieRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val movieMapper: MovieListDataDomainMapper,
    private val dispatcher: CoroutineDispatcher
) : BaseRepository(), MovieRepository {

    override suspend fun fetchMovieList(): Resource<MovieDomainModel> {
        return fetchAPiData(
            { apiService.getMovieList() }, movieMapper, dispatcher
        )

    }
}