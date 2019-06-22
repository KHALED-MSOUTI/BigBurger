package com.khmsouti.bigburger.networking

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitAdapter {
    private var sRetrofit: Retrofit? = null
    private var sGson: Gson? = null
    private val sURL: String = "http://legacy.vibuy.com/dump/"

    @Synchronized
    fun getInstanced(): Retrofit? {
        if (sRetrofit == null) {
            if (sGson == null) {
                sGson = GsonBuilder().setLenient().create()
            }

             sRetrofit = Retrofit.Builder()
                .baseUrl(sURL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        }
        return sRetrofit
    }

}


