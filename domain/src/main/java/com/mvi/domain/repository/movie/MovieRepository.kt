package com.mvi.domain.repository.movie

import com.mvi.common.Resource
import com.mvi.domain.model.movie.MovieDomainModel

/**
 * Methods of Repository
 */
interface MovieRepository {
    suspend fun fetchMovieList(): Resource<MovieDomainModel>
}