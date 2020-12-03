package ru.mikhailskiy.intensiv.data

import com.google.gson.annotations.SerializedName

data class MovieModel(
    @SerializedName("id")  val id: Int,
    @SerializedName("title")  val title: String?,
    @SerializedName("poster_path")  val poster: String?,
    @SerializedName("vote_average")  val voteAverage: Float?,
    @SerializedName("overview")  val overview: String?,
    @SerializedName("genres")  val genres: List<Genre>?,
    @SerializedName("production_companies")  val productionCompanies: List<Production>?,
    @SerializedName("release_date")  val releaseDate: String?

)

