package com.khmsouti.bigburger

import com.khmsouti.bigburger.activity.MainActivity
import org.junit.Assert
import org.junit.Test

class MainActivityTest {

    private val mMainActivity = MainActivity()
    @Test
    fun isFirstUseTest() {
        Assert.assertEquals(true, mMainActivity.isFirstUse)
    }






}