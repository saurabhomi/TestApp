package com.mvi.data.model.movie

data class MovieListDataModel(
    val id: Long?,
    val originalTitle: String?,
    val overview: String?,
    val posterPath: String?,
    val voteAverage: Float?
)