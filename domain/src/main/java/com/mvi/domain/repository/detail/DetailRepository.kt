package com.mvi.domain.repository.detail


import com.mvi.base.Resource
import com.mvi.domain.entity.detail.DetailEntityModel

interface DetailRepository {

    suspend fun fetchMovieDetail(id: String): Resource<DetailEntityModel>
}