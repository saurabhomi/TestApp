package com.mvi.data.source.detail

import com.mvi.data.mapper.detail.DetailNetworkDataMapper
import com.mvi.data.model.detail.DetailDataModel
import com.mvi.network.api.ApiService
import javax.inject.Inject

class DetailRemoteDataSourceImpl @Inject constructor(
    private val apiService: ApiService,
    private val movieMapper: DetailNetworkDataMapper
) : DetailRemoteDataSource {

    override suspend fun fetchMovieDetail(id: String): DetailDataModel {
        val networkData = apiService.getMovieDetails(id)
        return movieMapper.from(requireNotNull(networkData.body()))
    }
}