package ru.mikhailskiy.intensiv.ui.search

import ru.mikhailskiy.intensiv.data.MovieModel

interface SearchContract {
    interface View{
        fun showSearchResult(movies: List<MovieModel>?)
        fun showInputError()
        fun showProgress(isVisible: Boolean)
    }

    interface Presenter{
        fun searchRequest(text: String)
        fun attachView(view: View)
        fun detachView()
    }
}