package com.mvi.domain.usecase

import com.mvi.base.BaseUseCase
import com.mvi.base.Resource
import com.mvi.base.qualifiers.IoDispatcher
import com.mvi.domain.entity.movie.MovieEntityModel
import com.mvi.domain.repository.movie.MovieRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Use Case class for get movie list
 */
class GetMovieListUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : BaseUseCase<MovieEntityModel, Nothing> {

    suspend operator fun invoke(): Resource<MovieEntityModel> {
        return withContext(dispatcher) {
            movieRepository.fetchMovieList()
        }
    }
}