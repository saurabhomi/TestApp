package com.mvi.data.mapper.movie

import com.mvi.common.Mapper
import com.mvi.data.model.movie.MovieDataModel
import com.mvi.domain.model.movie.MovieDomainModel
import com.mvi.network.constant.NetworkConstants
import javax.inject.Inject

/**
 * Mapper class for convert [MovieDataModel] to [MovieDomainModel] and vice versa
 */
class MovieListDataDomainMapper @Inject constructor() :
    Mapper<MovieDataModel, MovieDomainModel> {

    override fun from(i: MovieDataModel): MovieDomainModel {
        return MovieDomainModel(result = i.result!!.map {
            MovieDomainModel.MovieListDomainModel(
                id = it.id!!,
                originalTitle = it.originalTitle!!,
                overview = it.overview!!,
                posterPath = "${NetworkConstants.IMAGE_URL}${it.posterPath}",
                voteAverage = it.voteAverage!!
            )
        }
        )
    }
}

