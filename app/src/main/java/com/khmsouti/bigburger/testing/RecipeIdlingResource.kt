package com.khmsouti.bigburger.testing


import androidx.test.espresso.IdlingResource

class RecipeIdlingResource(private val waitingTime: Long) : IdlingResource {

    //This class usage is for testing purposes
    private val startTime: Long = System.currentTimeMillis()
    private var callback: IdlingResource.ResourceCallback? = null

    override fun getName(): String {
        return RecipeIdlingResource::class.java.name + ":" + waitingTime
    }

    override fun isIdleNow(): Boolean {
        val elapsed = System.currentTimeMillis() - startTime
        val idle = elapsed >= waitingTime
        if (idle) {
            callback!!.onTransitionToIdle()
        }
        return idle
    }

    override fun registerIdleTransitionCallback(resourceCallback: IdlingResource.ResourceCallback) {
        this.callback = resourceCallback
    }
}