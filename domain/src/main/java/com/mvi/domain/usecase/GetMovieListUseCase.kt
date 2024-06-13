package com.mvi.domain.usecase

import com.mvi.domain.repository.movie.MovieRepository
import javax.inject.Inject

/**
 * Use Case class for get movie list
 */
class GetMovieListUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
) {
    suspend operator fun invoke() = movieRepository.fetchMovieList()
}