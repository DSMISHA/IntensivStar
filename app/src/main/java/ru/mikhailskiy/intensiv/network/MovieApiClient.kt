package ru.mikhailskiy.intensiv.network

import com.google.gson.GsonBuilder
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.mikhailskiy.intensiv.BuildConfig


object MovieApiClient {

    //todo add cache

    private val client: OkHttpClient = OkHttpClient.Builder().apply {
        if(BuildConfig.DEBUG) {
            addInterceptor(HttpLoggingInterceptor().apply {
                this.level = HttpLoggingInterceptor.Level.BODY
            })
        }
    }.build()

    val apiClient: MovieApiInterface by lazy{
        val gson = GsonBuilder()
            .setLenient()
            .create()

        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
            .create(MovieApiInterface::class.java)
    }

}
