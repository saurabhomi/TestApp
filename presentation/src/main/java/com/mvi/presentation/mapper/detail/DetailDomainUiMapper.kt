package com.mvi.presentation.mapper.detail

import com.mvi.base.Mapper
import com.mvi.common.constants.Constants
import com.mvi.domain.entity.detail.DetailEntityModel
import com.mvi.presentation.model.detail.DetailUiModel
import javax.inject.Inject

/**
 * Mapper class for convert [DetailEntityModel] to [DetailUiModel]
 */
class DetailDomainUiMapper @Inject constructor() :
    Mapper<DetailEntityModel, DetailUiModel> {

    override fun from(i: DetailEntityModel?): DetailUiModel {
        return DetailUiModel(
            adult = i?.adult,
            backdropPath = i?.backdropPath,
            id = i?.id,
            originalLanguage = i?.originalLanguage,
            originalTitle = i?.originalTitle,
            overview = i?.overview,
            popularity = i?.popularity,
            posterPath = "${Constants.IMAGE_URL}${i?.posterPath}",
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