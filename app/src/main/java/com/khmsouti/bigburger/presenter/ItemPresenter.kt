package com.khmsouti.bigburger.presenter

import com.khmsouti.bigburger.callback.Callback
import com.khmsouti.bigburger.contract.MainActivityContract
import com.khmsouti.bigburger.model.Item
import com.khmsouti.bigburger.utils.MyTask

class ItemPresenter( var mView: MainActivityContract.View) : MainActivityContract.Presenter {

    //Presenters, part of MVP architecture

    override fun start() {
        mView.init()
    }

    override fun getItems() {
        MyTask().getJsonCallBack(object : Callback<ArrayList<Item>>() {
            override fun getResult(t: ArrayList<Item>) {
                mView.loadData(t)
            }

            override fun returnError(message: String) {
                mView.showError(message)
            }

        })
    }
}