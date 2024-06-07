package com.mvi.data.mapper.detail


import com.mvi.base.Mapper
import com.mvi.data.model.detail.DetailDataModel
import com.mvi.network.model.detail.DetailNetworkModel
import javax.inject.Inject

/**
 * Mapper class for convert [DetailNetworkModel] to [DetailDataModel] and vice versa
 */
class DetailNetworkDataMapper @Inject constructor() :
    Mapper<DetailNetworkModel, DetailDataModel> {

    override fun from(i: DetailNetworkModel?): DetailDataModel {
        return DetailDataModel(
            adult = i?.adult,
            backdropPath = i?.backdropPath,
            id = i?.id,
            originalLanguage = i?.originalLanguage,
            originalTitle = i?.originalTitle,
            overview = i?.overview,
            popularity = i?.popularity,
            posterPath = i?.posterPath,
            releaseDate = i?.releaseDate,
            runtime = i?.runtime,
            status = i?.status,
            tagline = i?.tagline,
            title = i?.title,
            voteAverage = i?.voteAverage,
            voteCount = i?.voteCount,
        )
    }
}