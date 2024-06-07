package com.mvi.network.model.movie

import com.google.gson.annotations.SerializedName

data class MovieListNetworkModel(
    @SerializedName("id") val id: Long?,
    @SerializedName("original_title") val originalTitle: String?,
    @SerializedName("overview") val overview: String?,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("vote_average") val voteAverage: Float?
)