package com.khmsouti.bigburger

interface CartActivityContract {
    interface View {

        fun init()
        fun loadData()
    }

    interface Presenter {
        fun Start()
        fun fillData()
    }
}