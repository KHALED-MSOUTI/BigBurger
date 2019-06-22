package com.khmsouti.bigburger.callback

abstract class Callback<T> {
    abstract fun  getResult(t:T)
    abstract fun returnError(message : String)
}