package com.mvi.data.model.detail

import com.google.gson.annotations.SerializedName

data class DetailDataModel(
    val adult: Boolean?,
    @SerializedName("backdrop_path") val backdropPath: String?, val id: Long?,
    @SerializedName("original_language") val originalLanguage: String?,
    @SerializedName("original_title") val originalTitle: String?,
    val overview: String?, val popularity: Double?,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("release_date") val releaseDate: String?,
    val runtime: Long?,
    val status: String?, val tagline: String?, val title: String?,
    @SerializedName("vote_average") val voteAverage: Double?,
    @SerializedName("vote_count") val voteCount: Long?
)
