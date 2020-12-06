package ru.mikhailskiy.intensiv.network

import android.app.DownloadManager
import io.reactivex.Observable
import ru.mikhailskiy.intensiv.data.*

interface RestApiInterface{
    fun getPopularMovies(): Observable<MovieWrapper?>?
    fun getWatchingMovies(): Observable<MovieWrapper?>?
    fun getNewMovies(): Observable<MovieWrapper?>?
    fun getTvShows(): Observable<TvShowWrapper?>?
    fun getDetails(id: Int): Observable<MovieModel?>?
    fun getMovieCast(id: Int): Observable<Cast?>?
    fun searchMovie(query: String): Observable<MovieWrapper?>?
}


object RestApi : RestApiInterface{

    override fun getPopularMovies(): Observable<MovieWrapper?>?  {
        val page = 1
        return MovieApiClient.apiClient.getPopularMovies(page)
    }

    override fun getWatchingMovies(): Observable<MovieWrapper?>?  {
        val page = 1
        return MovieApiClient.apiClient.getWatchingMovies(page)
    }

    override fun getNewMovies(): Observable<MovieWrapper?>?  {
        val page = 1
        return MovieApiClient.apiClient.getNewMovies(page)
    }

    override fun getTvShows(): Observable<TvShowWrapper?>?  {
        val page = 1
        return MovieApiClient.apiClient.getTvShows(page)
    }

    override fun getDetails(id: Int): Observable<MovieModel?>?  {
        return MovieApiClient.apiClient.getMovieDetails(id)
    }

    override fun getMovieCast(id: Int): Observable<Cast?>? {
        return MovieApiClient.apiClient.getMovieCast(id)
    }

    override fun searchMovie(query: String): Observable<MovieWrapper?>? {
        val page = 1
        return MovieApiClient.apiClient.searchMovie(query, page)
    }
}