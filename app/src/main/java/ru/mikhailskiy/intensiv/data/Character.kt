package ru.mikhailskiy.intensiv.data

import java.io.Serializable

//fixme to remove serializable and take movie from repo by id. Same for character
data class Character(val image: String? = null, val name: String? = null): Serializable
