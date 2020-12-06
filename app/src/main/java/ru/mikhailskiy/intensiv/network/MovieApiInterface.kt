package ru.mikhailskiy.intensiv.network

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.mikhailskiy.intensiv.BuildConfig
import ru.mikhailskiy.intensiv.data.*


interface MovieApiInterface {
    companion object {
        private const val QUARRY_API_KEY = "?api_key=" + BuildConfig.THE_MOVIE_DATABASE_API
        private const val QUARRY_LANGUAGE = "&language=en-US"
    }

    @GET("movie/popular$QUARRY_API_KEY$QUARRY_LANGUAGE")
    fun getPopularMovies(
        @Query("page") page: Int
    ): Observable<MovieWrapper?>?

    @GET("movie/now_playing$QUARRY_API_KEY$QUARRY_LANGUAGE")
    fun getWatchingMovies(
        @Query("page") page: Int
    ): Observable<MovieWrapper?>?

    @GET("movie/upcoming$QUARRY_API_KEY$QUARRY_LANGUAGE")
    fun getNewMovies(
        @Query("page") page: Int
    ): Observable<MovieWrapper?>?

    @GET("tv/popular$QUARRY_API_KEY$QUARRY_LANGUAGE")
    fun getTvShows(
        @Query("page") page: Int
    ): Observable<TvShowWrapper?>?

    @GET("movie/{movie_id}$QUARRY_API_KEY")
    fun getMovieDetails(
        @Path("movie_id") id: Int
    ): Observable<MovieModel?>?

    @GET("movie/{movie_id}/credits$QUARRY_API_KEY")
    fun getMovieCast(
        @Path("movie_id") id: Int
    ): Observable<Cast?>?

    @GET("search/movie$QUARRY_API_KEY$QUARRY_LANGUAGE")
    fun searchMovie(
        @Query("query") query: String,
        @Query("page") page: Int
    ): Observable<MovieWrapper?>?
}




