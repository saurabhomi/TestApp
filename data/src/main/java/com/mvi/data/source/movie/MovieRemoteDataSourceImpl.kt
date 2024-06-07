package com.mvi.data.source.movie

import com.mvi.data.mapper.movie.MovieNetworkDataMapper
import com.mvi.data.model.movie.MovieDataModel
import com.mvi.network.api.ApiService
import javax.inject.Inject

class MovieRemoteDataSourceImpl @Inject constructor(
    private val apiService: ApiService,
    private val movieMapper: MovieNetworkDataMapper
) : MovieRemoteDataSource {

    override suspend fun fetchMovieList(): MovieDataModel {
        val networkData = apiService.getMovieList()
        return movieMapper.from(networkData.body())
    }
}