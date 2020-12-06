package ru.mikhailskiy.intensiv.network

import io.reactivex.observers.DisposableObserver


abstract class SimpleSubscriber<T>() : DisposableObserver<T>() {
    override fun onError(e: Throwable) { e.printStackTrace() }
    override fun onComplete() {}
}

