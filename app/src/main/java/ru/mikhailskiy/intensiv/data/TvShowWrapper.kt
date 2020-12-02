package ru.mikhailskiy.intensiv.data

import com.google.gson.annotations.SerializedName
import ru.mikhailskiy.intensiv.network.TvShowModel

data class TvShowWrapper(
    @SerializedName("results") val result: List<TvShowModel>?
)