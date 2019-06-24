package com.khmsouti.bigburger

import org.junit.Assert
import org.junit.Test

class MainActivityTest {

    private val tested = MainActivity()
    @Test
    fun isFirstUseTest() {
        Assert.assertEquals(true, tested.isFirstUse)
    }


    @Test
    fun getCartListTest() {
    }

    @Test
    fun loadDataTest() {

    }

    @Test
    fun deleteFromCart() {
    }

    @Test
    fun addToCart() {
    }
}