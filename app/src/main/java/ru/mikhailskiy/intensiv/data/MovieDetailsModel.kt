package ru.mikhailskiy.intensiv.data

import com.google.gson.annotations.SerializedName

data class MovieDetailsModel(
    @SerializedName("adult") val isAdult: Boolean?
)