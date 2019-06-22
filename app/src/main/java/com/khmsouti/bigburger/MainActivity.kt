package com.khmsouti.bigburger

import android.content.SharedPreferences
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.khmsouti.bigburger.adapter.MyAdapter
import com.khmsouti.bigburger.model.Item
import com.khmsouti.bigburger.presenter.ItemPresenter
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt


class MainActivity : AppCompatActivity(), MainActivityContract.View {

    private lateinit var mPresenter: ItemPresenter
    private lateinit var recyclerView: RecyclerView
    private lateinit var myAdapter: MyAdapter
    private lateinit var list: ArrayList<Item>
    private lateinit var constraintLayout: ConstraintLayout
    // This boolean will used to know if the user is opening the application for the first time or not
    private var isFirstUse: Boolean = true

    private lateinit var pref: SharedPreferences
    private lateinit var prefEditor: SharedPreferences.Editor
    override fun onCreate(savedInstanceState: Bundle?) {

        /*
        TODO edit action bar transparency or fix it's color
        window.requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY)
        val actionBar = supportActionBar
        actionBar!!.setBackgroundDrawable(ColorDrawable(Color.parseColor("#000000ff")))
        actionBar.setStackedBackgroundDrawable(ColorDrawable(Color.parseColor("#000000ff")))
        */
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mPresenter = ItemPresenter(this)
        mPresenter.Start()

        pref = this.getPreferences(0)
        prefEditor = pref.edit()



        //Handle swipe action for recyclerView and icon
        val myCallbackRight = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                setSnackBar(
                    constraintLayout,
                    "${list[viewHolder.adapterPosition].getTitle()} added to the cart".toString()
                )
                //TODO Add swiped item to the cart
                myAdapter.notifyDataSetChanged()
            }
        }
        val myCallbackLeft = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                setSnackBar(
                    constraintLayout,
                    "${list[viewHolder.adapterPosition].getTitle()} Removed from the cart".toString()
                )
                //TODO Add swiped item to the cart
                myAdapter.notifyDataSetChanged()
            }
        }
        val helperRight = ItemTouchHelper(myCallbackRight)
        helperRight.attachToRecyclerView(recyclerView)
        val helperLeft = ItemTouchHelper(myCallbackLeft)
        helperLeft.attachToRecyclerView(recyclerView)
    }

    override fun init() {
        constraintLayout = findViewById(R.id.mainRootLayout)
        recyclerView = findViewById(R.id.mainRecyclerView)
        val manager: RecyclerView.LayoutManager
        manager = androidx.recyclerview.widget.GridLayoutManager(applicationContext, 2)
        recyclerView.layoutManager = manager
        mPresenter.getItems()
    }

    override fun showError(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
    }

    override fun loadData(ItemList: ArrayList<Item>) {
        list = ItemList
        myAdapter = MyAdapter(ItemList, this)
        recyclerView.adapter = myAdapter
        isFirstUse = pref.getBoolean("isFirstUse", true)
        runSpotLight()
    }

    private fun runSpotLight() {
        if (isFirstUse) {
        MaterialTapTargetPrompt.Builder(this@MainActivity)
            .setTarget(R.id.view)
            //TODO put hard coded string in String.xml
            .setPrimaryText("You can swipe left to add the item to the cart")
            .setSecondaryText("Or you can delete it from the cart by swiping left !")
            .setPromptStateChangeListener { _, _ ->
            }
            .show()
            isFirstUse = false
            prefEditor.putBoolean("isFirstUse", isFirstUse)
            prefEditor.commit()
        }
    }

    //Set SnackBar configurations
    fun setSnackBar(root: View, snackTitle: String) {
        val mSnackbar = Snackbar.make(root, snackTitle, Snackbar.LENGTH_SHORT)
        mSnackbar.show()
        val view = mSnackbar.view
        val mTextView = view.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
        mTextView.gravity = Gravity.CENTER_HORIZONTAL
    }

}
