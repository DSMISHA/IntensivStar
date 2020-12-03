package ru.mikhailskiy.intensiv.data

import com.google.gson.annotations.SerializedName

data class Character(
    @SerializedName("id") val id:Int,
    @SerializedName("profile_path") val image: String? = null,
    @SerializedName("name") val name: String? = null
)
