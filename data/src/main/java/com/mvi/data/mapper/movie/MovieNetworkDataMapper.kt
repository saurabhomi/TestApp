package com.mvi.data.mapper.movie

import com.mvi.base.Mapper
import com.mvi.data.model.movie.MovieDataModel
import com.mvi.data.model.movie.MovieListDataModel
import com.mvi.network.model.movie.MovieNetworkModel
import javax.inject.Inject

/**
 * Mapper class for convert [MovieNetworkModel] to [MovieListDataModel] and vice versa
 */
class MovieNetworkDataMapper @Inject constructor() :
    Mapper<MovieNetworkModel, MovieDataModel> {

    override fun from(i: MovieNetworkModel?): MovieDataModel {
        return MovieDataModel(result = i?.result?.map {
            MovieListDataModel(
                id = it.id,
                originalTitle = it.originalTitle,
                overview = it.overview,
                posterPath = it.posterPath,
                voteAverage = it.voteAverage
            )
        })
    }


}