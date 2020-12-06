package ru.mikhailskiy.intensiv.data

import com.google.gson.annotations.SerializedName

data class Production (
    @SerializedName("id") val id: Int,
    @SerializedName("name") val title: String?
)