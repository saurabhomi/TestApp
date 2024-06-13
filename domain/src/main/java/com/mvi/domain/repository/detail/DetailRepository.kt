package com.mvi.domain.repository.detail

import com.mvi.common.Resource
import com.mvi.domain.model.detail.DetailDomainModel

interface DetailRepository {
    suspend fun fetchMovieDetail(id: String): Resource<DetailDomainModel>
}