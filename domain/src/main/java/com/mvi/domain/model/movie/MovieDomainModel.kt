package com.mvi.domain.model.movie

data class MovieDomainModel(
    val result: List<MovieListDomainModel>
) {
    data class MovieListDomainModel(
        val id: Long,
        val originalTitle: String,
        val overview: String,
        val posterPath: String,
        val voteAverage: Float
    )
}