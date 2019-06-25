package com.khmsouti.bigburger.presenter

import com.khmsouti.bigburger.contract.CartActivityContract

class CartPresenter(private var mView: CartActivityContract.View) : CartActivityContract.Presenter {

    //Presenters, part of MVP architecture

    override fun start() {
        mView.init()
    }

    override fun fillData() {
        mView.loadData()
    }

}