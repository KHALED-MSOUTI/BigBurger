package com.khmsouti.bigburger.presenter

import com.khmsouti.bigburger.MainActivityContract
import com.khmsouti.bigburger.Utils.myTask
import com.khmsouti.bigburger.callback.Callback
import com.khmsouti.bigburger.model.Item

class ItemPresenter( var mView: MainActivityContract.View) : MainActivityContract.Presenter {

    override fun Start() {
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