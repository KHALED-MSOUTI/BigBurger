package com.khmsouti.bigburger.networking

class NetworkingUtils {
    private var userService: MyService? = null


    //Make Retrofit2 call
    fun getApiInstance(): MyService? {
        if (userService == null)
            userService = RetrofitAdapter().getInstanced()?.create(MyService::class.java)

        return userService
    }
}