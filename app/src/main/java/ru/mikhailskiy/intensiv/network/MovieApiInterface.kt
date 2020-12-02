package ru.mikhailskiy.intensiv.network

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.mikhailskiy.intensiv.data.MovieDetailsModel
import ru.mikhailskiy.intensiv.data.MovieWrapper
import ru.mikhailskiy.intensiv.data.TvShowWrapper


interface MovieApiInterface {

    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("language") lang: String,
        @Query("page") page: Int
    ): Observable<MovieWrapper?>?

    @GET("movie/now_playing")
    fun getWatchingMovies(
        @Query("api_key") apiKey: String,
        @Query("language") lang: String,
        @Query("page") page: Int
    ): Observable<MovieWrapper?>?

    @GET("movie/upcoming")
    fun getNewMovies(
        @Query("api_key") apiKey: String,
        @Query("language") lang: String,
        @Query("page") page: Int
    ): Observable<MovieWrapper?>?

    @GET("tv/popular")
    fun getTvShows(
        @Query("api_key") apiKey: String,
        @Query("language") lang: String,
        @Query("page") page: Int
    ): Observable<TvShowWrapper?>?

    @GET("movie/{movie_id}")
    fun getMovieDetails(
        @Path("movie_id") id: Int,
        @Query("api_key") apiKey: String
    ): Observable<MovieDetailsModel?>?


    //todo
    // https://developers.themoviedb.org/3/movies/get-movie-details
    // https://developers.themoviedb.org/3/movies/get-movie-credits
}




