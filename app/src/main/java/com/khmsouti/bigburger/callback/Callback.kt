package com.khmsouti.bigburger.callback


//Utility class to make API calls
//A custom callback class which we can use to receive errors and results.
abstract class Callback<T> {
    abstract fun  getResult(t:T)
    abstract fun returnError(message : String)
}