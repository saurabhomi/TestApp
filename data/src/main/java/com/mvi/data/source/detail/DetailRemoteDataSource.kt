package com.mvi.data.source.detail

import com.mvi.data.model.detail.DetailDataModel

interface DetailRemoteDataSource {

    suspend fun fetchMovieDetail(id: String): DetailDataModel

}