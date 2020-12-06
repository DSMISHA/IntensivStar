package ru.mikhailskiy.intensiv.ui.search

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import ru.mikhailskiy.intensiv.MovieFinderApp
import ru.mikhailskiy.intensiv.data.MovieWrapper
import ru.mikhailskiy.intensiv.network.SimpleSubscriber
import java.lang.ref.WeakReference
import java.util.concurrent.TimeUnit


class SearchPresenter : SearchContract.Presenter {

    private val minLength = 3
    private var wView: WeakReference<SearchContract.View>? = null
    private val subject: PublishSubject<String> = PublishSubject.create()
    private val api = MovieFinderApp.instance?.getRestApi()
    private val disposable : CompositeDisposable by lazy{ CompositeDisposable() }

    override fun searchRequest(text: String) {
        if(text.length > minLength) {
            subject.onNext(text)
        }
    }

    override fun attachView(view: SearchContract.View) {
        this.wView = WeakReference(view)
        val searchSubscriber = subject
            .debounce(400, TimeUnit.MILLISECONDS)
            .map { obj: CharSequence -> obj.toString() }
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(Schedulers.io())
            .doOnNext{getView()?.showProgress(true)}
            .switchMap<MovieWrapper> { query: String -> api?.searchMovie(query) }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { getView()?.showProgress(false) }
            .doOnError {
                getView()?.showProgress(false)
                it.printStackTrace()
            }
            .subscribeWith(object : SimpleSubscriber<MovieWrapper>() {
                override fun onNext(t: MovieWrapper) {
                    if (isViewExists()) {
                        getView()?.showSearchResult(t.result)
                    }
                }
            })

        disposable.add(searchSubscriber)
    }



    override fun detachView() {
       disposable.clear()
    }

    private fun isViewExists(): Boolean{
        return getView() != null
    }

    private fun getView(): SearchContract.View?{
        return wView?.get()
    }

}