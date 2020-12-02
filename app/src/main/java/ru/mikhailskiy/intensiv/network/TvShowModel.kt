package ru.mikhailskiy.intensiv.network

import com.google.gson.annotations.SerializedName

data class TvShowModel(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val title: String?,
    @SerializedName("poster_path") val poster: String?,
    @SerializedName("vote_average") val voteAverage: Float?
)