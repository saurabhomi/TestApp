package com.mvi.network.model.movie

import com.google.gson.annotations.SerializedName


data class MovieNetworkModel(
    @SerializedName("results") val result: List<MovieListNetworkModel>?
)