package ru.mikhailskiy.intensiv.ui.search

interface SearchContract {
    interface View{
        fun showSearchResult()
        fun showInputError()
    }

    interface Presenter{
        fun searchRequest(text: String)
        fun attachView(view: View)
        fun detachView()
    }
}