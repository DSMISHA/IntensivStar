package ru.mikhailskiy.intensiv.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query


interface MovieApiInterface {

    //todo get movies

    //todo get popular

    //todo get tvshows

    //todo get movie by id


    @GET("movies")
    fun getMovies(): Call<Boolean?>?
}
