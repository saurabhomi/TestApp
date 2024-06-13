package com.mvi.data.model.movie

import com.google.gson.annotations.SerializedName

data class MovieDataModel(
    @SerializedName("results") val result: List<MovieListDataModel>?
) {
    data class MovieListDataModel(
        @SerializedName("id") val id: Long?,
        @SerializedName("original_title") val originalTitle: String?,
        @SerializedName("overview") val overview: String?,
        @SerializedName("poster_path") val posterPath: String?,
        @SerializedName("vote_average") val voteAverage: Float?
    )
}