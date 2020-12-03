package ru.mikhailskiy.intensiv.network

import io.reactivex.Observable
import ru.mikhailskiy.intensiv.BuildConfig
import ru.mikhailskiy.intensiv.data.*
import java.io.IOException

interface RestApiInterface{
    fun getPopularMovies(): Observable<MovieWrapper?>?
    fun getWatchingMovies(): Observable<MovieWrapper?>?
    fun getNewMovies(): Observable<MovieWrapper?>?
    fun getTvShows(): Observable<TvShowWrapper?>?
    fun getDetails(id: Int): Observable<MovieModel?>?
    fun getMovieCast(id: Int): Observable<Cast?>?
}


object RestApi : RestApiInterface{

    override fun getPopularMovies(): Observable<MovieWrapper?>?  {
        val lang = "en-US"
        val page = 1
        return MovieApiClient.apiClient.getPopularMovies(getKey(), lang, page)
    }

    override fun getWatchingMovies(): Observable<MovieWrapper?>?  {
        val lang = "en-US"
        val page = 1
        return MovieApiClient.apiClient.getWatchingMovies(getKey(), lang, page)
    }

    override fun getNewMovies(): Observable<MovieWrapper?>?  {
        val lang = "en-US"
        val page = 1
        return MovieApiClient.apiClient.getNewMovies(getKey(), lang, page)
    }

    override fun getTvShows(): Observable<TvShowWrapper?>?  {
        val lang = "en-US"
        val page = 1
        return MovieApiClient.apiClient.getTvShows(getKey(), lang, page)
    }

    override fun getDetails(id: Int): Observable<MovieModel?>?  {
        return MovieApiClient.apiClient.getMovieDetails(id, getKey())
    }

    override fun getMovieCast(id: Int): Observable<Cast?>? {
        return MovieApiClient.apiClient.getMovieCast(id, getKey())
    }

    private fun getKey(): String{
        return BuildConfig.THE_MOVIE_DATABASE_API
    }
}