package com.khmsouti.bigburger.contract

interface CartActivityContract {
    interface View {

        fun init()
        fun loadData()
    }

    interface Presenter {
        fun start()
        fun fillData()
    }
}