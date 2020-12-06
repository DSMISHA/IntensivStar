package ru.mikhailskiy.intensiv.data

import com.google.gson.annotations.SerializedName

data class TvShowModel(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val title: String?,
    @SerializedName("poster_path") val poster: String?,
    @SerializedName("vote_average") val voteAverage: Float?
){
    val rating: Float? get() = voteAverage?.div(2)
}