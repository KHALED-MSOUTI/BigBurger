package com.khmsouti.bigburger

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.andremion.counterfab.CounterFab
import com.google.android.material.snackbar.Snackbar
import com.khmsouti.bigburger.adapter.MainActivityRVAdapter
import com.khmsouti.bigburger.model.Item
import com.khmsouti.bigburger.presenter.ItemPresenter
import com.khmsouti.bigburger.utils.SwipeCallback
import kotlinx.android.synthetic.main.activity_main.*
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt


class MainActivity : AppCompatActivity(), MainActivityContract.View {

    private lateinit var mPresenter: ItemPresenter
    private lateinit var mainRecyclerView: RecyclerView
    private lateinit var mainActivityRVAdapter: MainActivityRVAdapter
    private lateinit var list: ArrayList<Item>
    private lateinit var mainRootLayout: ConstraintLayout

    var cartList: ArrayList<Item> = ArrayList()
    private lateinit var pref: SharedPreferences
    private lateinit var prefEditor: SharedPreferences.Editor
    private lateinit var counterFab: CounterFab

    // This boolean will used to know if the user is opening the application for the first time or not
    private var isFirstUse: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mPresenter = ItemPresenter(this)
        mPresenter.start()
        pref = this.getPreferences(0)
        prefEditor = pref.edit()
        prefEditor.apply()
    }

    override fun init() {
        mainRootLayout = findViewById(R.id.mainRootLayout)
        mainRecyclerView = findViewById(R.id.mainRecyclerView)
        val manager: RecyclerView.LayoutManager
        manager = androidx.recyclerview.widget.GridLayoutManager(applicationContext, 2)
        mainRecyclerView.layoutManager = manager
        counterFab = findViewById(R.id.counterFab)
        mPresenter.getItems()
        initSwipe()
    }

    override fun showError(message: String) {

        Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
        val intent = Intent(applicationContext, ConnectionErrorActivity::class.java)
        startActivity(intent)

    }

    override fun loadData(ItemList: ArrayList<Item>) {
        progressBar.visibility = View.VISIBLE
        val connectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnected ?: false
        if (isConnected) {
            progressBar.visibility = View.GONE
            list = ItemList
            mainActivityRVAdapter = MainActivityRVAdapter(ItemList, this)
            mainRecyclerView.adapter = mainActivityRVAdapter
            isFirstUse = pref.getBoolean("isFirstUse", true)
            runSpotLight()
            counterFab.setOnClickListener {
                //get the list , put it inside intent , send it to cart activity
                val intent = Intent(applicationContext, CartActivity::class.java)
                intent.putExtra(getString(R.string.INTENT_TAG_TO_CART_ACTIVITY), cartList)
                startActivity(intent)
            }
        }


    }

    private fun runSpotLight() {
        if (isFirstUse) {
            MaterialTapTargetPrompt.Builder(this@MainActivity)
                .setTarget(R.id.view)
                .setPrimaryText(getString(R.string.spotLightFirstText))
                .setSecondaryText(getString(R.string.spotLightSecondText))
                .setPromptStateChangeListener { _, _ ->
                }
                .show()
            isFirstUse = false
            prefEditor.putBoolean(getString(R.string.SHARED_PREFERENCES_FIRST_USE_BOOLEAN), isFirstUse)
            prefEditor.commit()
        }
    }

    //Set SnackBar configurations
    private fun setSnackBar(root: View, snackTitle: String) {
        val mSnackbar = Snackbar.make(root, snackTitle, Snackbar.LENGTH_SHORT)
        mSnackbar.show()
        val view = mSnackbar.view
        val mTextView = view.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
        mTextView.gravity = Gravity.CENTER_HORIZONTAL
    }

    @Synchronized
    fun deleteFromCart(ref: Int) {
        /*
        this method test every condition before delete the item from the cart
        it checks the cart if it is empty or not
        if this item is in the cart or not
        if the item in the cart this method will delete it
         */
        if (cartList.size != 0) {
            for (i in cartList) {
                if (i.getRef() == ref) {
                    setSnackBar(
                        mainRootLayout,
                        "${i.getTitle()} Removed from the cart"
                    )
                    cartList.remove(i)
                    break
                } else {
                    setSnackBar(mainRootLayout, "you have no ${list[ref - 1].getTitle()} in your cart")
                }
            }
        } else {
            setSnackBar(mainRootLayout, "your cart is empty")
        }
        counterFab.count = cartList.size
        if (counterFab.count == 0) {
            counterFab.visibility = View.GONE
        }
        mainActivityRVAdapter.notifyDataSetChanged()

    }

    @Synchronized
    fun addToCart(position: Int) {
        setSnackBar(
            mainRootLayout,
            "${list[position].getTitle()} added to the cart"
        )
        cartList.add(list[position])
        counterFab.count = cartList.size
        if (counterFab.count > 0) {
            counterFab.visibility = View.VISIBLE
        }
        mainActivityRVAdapter.notifyDataSetChanged()
    }

    private fun initSwipe() {
        val swipeHandler = object : SwipeCallback(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                if (direction == ItemTouchHelper.LEFT) {
                    deleteFromCart(list[viewHolder.adapterPosition].getRef())
                } else {
                    addToCart(viewHolder.adapterPosition)
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(mainRecyclerView)
    }

}
