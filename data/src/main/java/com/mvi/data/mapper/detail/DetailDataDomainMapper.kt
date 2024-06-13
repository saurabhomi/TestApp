package com.mvi.data.mapper.detail

import com.mvi.common.Mapper
import com.mvi.data.model.detail.DetailDataModel
import com.mvi.domain.model.detail.DetailDomainModel
import com.mvi.network.constant.NetworkConstants.Companion.IMAGE_URL
import javax.inject.Inject

/**
 * Mapper class for convert [DetailDataModel] to [DetailDomainModel]
 */
class DetailDataDomainMapper @Inject constructor() :
    Mapper<DetailDataModel, DetailDomainModel> {

    override fun from(i: DetailDataModel): DetailDomainModel {
        return DetailDomainModel(
            adult = i.adult!!,
            backdropPath = i.backdropPath!!,
            id = i.id!!,
            originalLanguage = i.originalLanguage!!,
            originalTitle = i.originalTitle!!,
            overview = i.overview!!,
            popularity = i.popularity!!,
            posterPath = "${IMAGE_URL}${i.posterPath}",
            releaseDate = i.releaseDate!!,
            runtime = i.runtime!!,
            status = i.status!!,
            tagline = i.tagline!!,
            title = i.title!!,
            voteAverage = i.voteAverage!!,
            voteCount = i.voteCount!!,
        )
    }
}