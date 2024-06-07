package com.mvi.network.api

import com.mvi.network.BuildConfig
import com.mvi.network.model.detail.DetailNetworkModel
import com.mvi.network.model.movie.MovieNetworkModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * The main services that handles all endpoint processes
 */
interface ApiService {

    /**
     * Get Movie List list
     */
    @GET("discover/movie")
    suspend fun getMovieList(@Query("api_key") apiKey: String = BuildConfig.API_KEY): Response<MovieNetworkModel>

    @GET("movie/{MOVIE_ID}")
    suspend fun getMovieDetails(
        @Path("MOVIE_ID") movieId: String,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): Response<DetailNetworkModel>

}