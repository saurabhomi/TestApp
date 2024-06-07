package com.mvi.data.mapper.movie

import com.mvi.base.Mapper
import com.mvi.data.model.movie.MovieDataModel
import com.mvi.data.model.movie.MovieListDataModel
import com.mvi.domain.entity.movie.MovieEntityModel
import com.mvi.domain.entity.movie.MovieListEntityModel
import javax.inject.Inject

/**
 * Mapper class for convert [MovieListDataModel] to [MovieListEntityModel] and vice versa
 */
class MovieListDataDomainMapper @Inject constructor() :
    Mapper<MovieDataModel, MovieEntityModel> {

    override fun from(i: MovieDataModel?): MovieEntityModel {
        return MovieEntityModel(result = i?.result?.map {
            MovieListEntityModel(
                id = it.id!!,
                originalTitle = it.originalTitle!!,
                overview = it.overview!!,
                posterPath = it.posterPath!!,
                voteAverage = it.voteAverage!!
            )
        }

        )
    }
}

