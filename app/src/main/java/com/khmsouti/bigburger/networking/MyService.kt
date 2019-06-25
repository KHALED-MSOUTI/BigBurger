package com.khmsouti.bigburger.networking

import com.khmsouti.bigburger.model.Item
import io.reactivex.Observable
import retrofit2.http.GET

//Interface to create methods that use Retrofit2 and it's arguments
interface MyService {
    @GET("mobiletest1.json")
    fun getJson(): Observable<ArrayList<Item>>
}
