package com.mvi.domain.entity.movie

data class MovieListEntityModel(
    val id: Long,
    val originalTitle: String,
    val overview: String,
    val posterPath: String,
    val voteAverage: Float
)