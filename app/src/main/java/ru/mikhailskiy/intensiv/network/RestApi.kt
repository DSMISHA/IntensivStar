package ru.mikhailskiy.intensiv.network

import io.reactivex.Observable
import ru.mikhailskiy.intensiv.BuildConfig
import ru.mikhailskiy.intensiv.data.MovieDetailsModel
import ru.mikhailskiy.intensiv.data.MovieWrapper
import ru.mikhailskiy.intensiv.data.TvShowWrapper

interface RestApiInterface{
    fun getPopularMovies(): Observable<MovieWrapper?>?
    fun getNewMovies(): Observable<MovieWrapper?>?
    fun getTvShows(): Observable<TvShowWrapper?>?
    fun getDetails(id: Int): Observable<MovieDetailsModel?>?
}


object RestApi : RestApiInterface{

    override fun getPopularMovies(): Observable<MovieWrapper?>?  {
        val lang = "en-US"
        val page = 1
        return MovieApiClient.apiClient.getPopularMovies(getKey(), lang, page)
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

    override fun getDetails(id: Int): Observable<MovieDetailsModel?>?  {
        return MovieApiClient.apiClient.getMovieDetails(id, getKey())
    }

    private fun getKey(): String{
        return BuildConfig.THE_MOVIE_DATABASE_API
    }
}