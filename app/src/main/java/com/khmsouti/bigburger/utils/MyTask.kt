package com.khmsouti.bigburger.utils

import com.khmsouti.bigburger.callback.Callback
import com.khmsouti.bigburger.model.Item
import com.khmsouti.bigburger.networking.NetworkingUtils
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.*

class MyTask {
    //Make retrofit call and link it by rxJva
    internal fun getJsonCallBack(callback: Callback<ArrayList<Item>>) {
        NetworkingUtils().getApiInstance()
            ?.getJson()
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribeOn(Schedulers.io())
            ?.subscribe(object : Observer<ArrayList<Item>> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(users: ArrayList<Item>) {
                    callback.getResult(users)
                }

                override fun onError(e: Throwable) {
                    callback.returnError(e.message!!)
                }

                override fun onComplete() {

                }
            })

    }

}