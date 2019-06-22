package com.khmsouti.bigburger.networking

import retrofit2.create

class NetworkingUtils {
    private var userService: MyService? = null

    fun getApiInstance(): MyService? {
        if (userService == null)
            userService = RetrofitAdapter().getInstanced()?.create(MyService::class.java)

        return userService
    }
}