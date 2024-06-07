package com.mvi.presentation.model.movie

import java.io.Serializable


data class MovieListUiModel(
    val id: Long?,
    val originalTitle: String?,
    val overview: String?,
    val posterPath: String?,
    val voteAverage: Float?
) : Serializable