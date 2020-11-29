package ru.mikhailskiy.intensiv.data

import java.io.Serializable

class Movie(
    var title: String? = "",
    var voteAverage: Double = 0.0,
    var description: String? = null,
    var characters:List<Character>? = null,
    var studio: String? = null,
    var genre: String? = null,
    var year: Int? = null

) : Serializable{
    val rating: Float
        get() = voteAverage.div(2).toFloat()
}
