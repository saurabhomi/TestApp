package com.mvi.data.repository.detail


import com.mvi.base.BaseRepository
import com.mvi.base.Resource
import com.mvi.data.mapper.detail.DetailDataDomainMapper
import com.mvi.data.source.detail.DetailRemoteDataSource
import com.mvi.domain.entity.detail.DetailEntityModel
import com.mvi.domain.repository.detail.DetailRepository
import javax.inject.Inject

/**
 * Implementation class of [DetailRepository]
 */
class DetailRepositoryImpl @Inject constructor(
    private val detailRemoteDataSource: DetailRemoteDataSource,
    private val detailMapper: DetailDataDomainMapper,
) : BaseRepository(), DetailRepository {

    override suspend fun fetchMovieDetail(id: String): Resource<DetailEntityModel> {
        return fetchAPiData(
            { detailRemoteDataSource.fetchMovieDetail(id) }, detailMapper
        )

    }
}