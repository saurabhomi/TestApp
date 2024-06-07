package com.mvi.domain.repository.movie

import com.mvi.base.Resource
import com.mvi.domain.entity.movie.MovieEntityModel

/**
 * Methods of Repository
 */
interface MovieRepository {

    suspend fun fetchMovieList(): Resource<MovieEntityModel>

}