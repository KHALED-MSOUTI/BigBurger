package com.khmsouti.bigburger.presenter

import com.khmsouti.bigburger.MainActivityContract
import com.khmsouti.bigburger.callback.Callback
import com.khmsouti.bigburger.model.Item
import com.khmsouti.bigburger.utils.myTask

class ItemPresenter( var mView: MainActivityContract.View) : MainActivityContract.Presenter {


    override fun start() {
        mView.init()
    }

    override fun getItems() {
        myTask().getJsonCallBack(object : Callback<ArrayList<Item>>(){
            override fun getResult(t: ArrayList<Item>) {
                mView.loadData(t)
            }

            override fun returnError(message: String) {
                mView.showError(message)
            }

        })
    }
}