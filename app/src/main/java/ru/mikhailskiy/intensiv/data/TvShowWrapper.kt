package ru.mikhailskiy.intensiv.data

import com.google.gson.annotations.SerializedName

data class TvShowWrapper(
    @SerializedName("results") val result: List<TvShowModel>?
)