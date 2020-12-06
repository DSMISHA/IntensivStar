package ru.mikhailskiy.intensiv

import android.app.Application
import ru.mikhailskiy.intensiv.network.RestApi
import ru.mikhailskiy.intensiv.network.RestApiInterface
import timber.log.Timber

class MovieFinderApp : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        initDebugTools()
    }
    private fun initDebugTools() {
        if (BuildConfig.DEBUG) {
            initTimber()
        }
    }

    private fun initTimber() {
        Timber.plant(Timber.DebugTree())
    }

    companion object {
        var instance: MovieFinderApp? = null
            private set
    }

    fun getRestApi(): RestApiInterface {
        return RestApi
    }
}