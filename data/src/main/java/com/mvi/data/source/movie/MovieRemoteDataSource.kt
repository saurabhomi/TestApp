package com.mvi.data.source.movie

import com.mvi.data.model.movie.MovieDataModel

interface MovieRemoteDataSource {

    suspend fun fetchMovieList(): MovieDataModel

}