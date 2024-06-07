package com.mvi.presentation.model.detail


data class DetailUiModel(
    val adult: Boolean?,
    val backdropPath: String?,
    val id: Long?,
    val originalLanguage: String?,
    val originalTitle: String?,
    val overview: String?,
    val popularity: Double?,
    val posterPath: String?,
    val releaseDate: String?,
    val runtime: Long?,
    val status: String?,
    val tagline: String?,
    val title: String?,
    val voteAverage: Double?,
    val voteCount: Long?
)
