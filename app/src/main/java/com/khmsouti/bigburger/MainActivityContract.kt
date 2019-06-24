package com.khmsouti.bigburger

import com.khmsouti.bigburger.model.Item

interface MainActivityContract {
    interface View{

        fun init()
        fun showError(message : String)
        fun loadData(ItemList :ArrayList<Item>)
    }

    interface Presenter{
        fun start()
        fun getItems()
    }
}