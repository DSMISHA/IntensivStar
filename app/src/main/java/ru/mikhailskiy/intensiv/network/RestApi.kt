package ru.mikhailskiy.intensiv.network

import io.reactivex.Observable
import ru.mikhailskiy.intensiv.BuildConfig
import ru.mikhailskiy.intensiv.data.MovieDetailsModel
import ru.mikhailskiy.intensiv.data.MovieWrapper
import ru.mikhailskiy.intensiv.data.TvShowWrapper

//todo add interface
object RestApi {

    fun getPopularMovies(): Observable<MovieWrapper?>?  {
        val lang = "en-US"
        val page = 1
        return MovieApiClient.apiClient.getPopularMovies(getKey(), lang, page)
    }

    fun getNewMovies(): Observable<MovieWrapper?>?  {
        val lang = "en-US"
        val page = 1
        return MovieApiClient.apiClient.getNewMovies(getKey(), lang, page)
    }

    fun getTvShows(): Observable<TvShowWrapper?>?  {
        val lang = "en-US"
        val page = 1
        return MovieApiClient.apiClient.getTvShows(getKey(), lang, page)
    }


    fun getDetails(id: Int): Observable<MovieDetailsModel?>?  {
        return MovieApiClient.apiClient.getMovieDetails(id, getKey())
    }

    private fun getKey(): String{
        return BuildConfig.THE_MOVIE_DATABASE_API
    }
}