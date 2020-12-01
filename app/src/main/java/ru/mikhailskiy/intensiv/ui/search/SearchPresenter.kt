package ru.mikhailskiy.intensiv.ui.search

import java.lang.ref.WeakReference


class SearchPresenter : SearchContract.Presenter {

    private val minLength = 3
    private var wView: WeakReference<SearchContract.View>? = null

    override fun searchRequest(text: String) {
        if(text.length > minLength){
//            val a = Observable.just(text)
//                .debounce(1500, TimeUnit.MILLISECONDS)
//                .subscribeOn(Schedulers.io()).observeOn(Schedulers.newThread()).subscribe {
//                        s -> println(s)
//                }
        }
    }

    override fun attachView(view: SearchContract.View) {
        this.wView = WeakReference(view)
    }

    override fun detachView() {
       //todo
    }

    private fun isViewExists(): Boolean{
        return getView() != null
    }

    private fun getView(): SearchContract.View?{
        return wView?.get()
    }

}