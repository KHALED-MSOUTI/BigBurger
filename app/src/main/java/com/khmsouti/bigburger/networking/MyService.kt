package com.khmsouti.bigburger.networking

import com.khmsouti.bigburger.model.Item
import io.reactivex.Observable

import retrofit2.http.GET
import kotlin.collections.ArrayList

interface MyService {
    @GET("mobiletest1.json")
    fun getJson(): Observable<ArrayList<Item>>
}
