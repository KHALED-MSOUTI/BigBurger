package com.khmsouti.bigburger.presenter

import com.khmsouti.bigburger.CartActivityContract

class CartPresenter(var mView: CartActivityContract.View) : CartActivityContract.Presenter {


    override fun Start() {
        mView.init()
    }

    override fun fillData() {
        mView.loadData()
    }

}