package com.khmsouti.bigburger

import com.khmsouti.bigburger.activity.CartActivity
import org.junit.Assert
import org.junit.Test

class CartActivityTest {
    private val cartActivity = CartActivity()
    @Test
    fun getPrice() {
        Assert.assertEquals(cartActivity.getPrice("1234"), 12.34f)
    }


}