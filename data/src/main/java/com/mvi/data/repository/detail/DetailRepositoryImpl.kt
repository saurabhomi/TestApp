package com.mvi.data.repository.detail

import com.mvi.common.Resource
import com.mvi.data.api.ApiService
import com.mvi.data.mapper.detail.DetailDataDomainMapper
import com.mvi.domain.model.detail.DetailDomainModel
import com.mvi.domain.repository.detail.DetailRepository
import com.mvi.network.base.BaseRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

/**
 * Implementation class of [DetailRepository]
 */
class DetailRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val detailMapper: DetailDataDomainMapper,
    dispatcher: CoroutineDispatcher
) : BaseRepository(dispatcher), DetailRepository {

    override suspend fun fetchMovieDetail(id: String): Resource<DetailDomainModel> {
        return fetchAPiData(
            { apiService.getMovieDetails(id) }, detailMapper
        )
    }
}