package com.mvi.presentation.mapper.movie

import com.mvi.base.Mapper
import com.mvi.common.constants.Constants
import com.mvi.domain.entity.movie.MovieEntityModel
import com.mvi.domain.entity.movie.MovieListEntityModel
import com.mvi.presentation.model.movie.MovieListUiModel
import com.mvi.presentation.model.movie.MovieUiModel
import javax.inject.Inject

/**
 * Mapper class for convert [MovieListEntityModel] to [MovieListUiModel] and vice versa
 */
class MovieListDomainUiMapper @Inject constructor() :
    Mapper<MovieEntityModel, MovieUiModel> {

    override fun from(i: MovieEntityModel?): MovieUiModel {
        return MovieUiModel(result = i?.result?.map {
            MovieListUiModel(
                id = it.id,
                originalTitle = it.originalTitle,
                overview = it.overview,
                posterPath = "${Constants.IMAGE_URL}${it.posterPath}",
                voteAverage = it.voteAverage
            )
        })
    }


}