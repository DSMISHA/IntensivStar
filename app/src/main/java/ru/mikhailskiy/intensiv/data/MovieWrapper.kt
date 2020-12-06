package ru.mikhailskiy.intensiv.data

import com.google.gson.annotations.SerializedName

data class MovieWrapper(
    @SerializedName("total_pages") val pagesCount: Int?,
    @SerializedName("results") val result: List<MovieModel>?
)