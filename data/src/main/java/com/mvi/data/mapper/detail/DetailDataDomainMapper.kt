package com.mvi.data.mapper.detail

import com.mvi.base.Mapper
import com.mvi.data.model.detail.DetailDataModel
import com.mvi.domain.entity.detail.DetailEntityModel
import javax.inject.Inject

/**
 * Mapper class for convert [DetailDataModel] to [DetailEntityModel]
 */
class DetailDataDomainMapper @Inject constructor() :
    Mapper<DetailDataModel, DetailEntityModel> {

    override fun from(i: DetailDataModel?): DetailEntityModel {
        return DetailEntityModel(
            adult = i?.adult!!,
            backdropPath = i.backdropPath!!,
            id = i.id!!,
            originalLanguage = i.originalLanguage!!,
            originalTitle = i.originalTitle!!,
            overview = i.overview!!,
            popularity = i.popularity!!,
            posterPath = i.posterPath!!,
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