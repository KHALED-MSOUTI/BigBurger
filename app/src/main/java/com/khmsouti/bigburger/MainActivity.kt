package com.khmsouti.bigburger

import android.graphics.Canvas
import android.graphics.Rect
import android.os.Bundle
import android.transition.Visibility
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.khmsouti.bigburger.adapter.MyAdapter
import com.khmsouti.bigburger.model.Item
import com.khmsouti.bigburger.presenter.ItemPresenter

import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt


class MainActivity : AppCompatActivity(), MainActivityContract.View {

    private lateinit var mPresenter: ItemPresenter
    private lateinit var recyclerView: RecyclerView
    private lateinit var myAdapter: MyAdapter
    private lateinit var addImageView : ImageView
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

        val addIcon = resources.getDrawable(
            R.drawable.ic_exposure_plus_1_black_24dp,
            null
        )

        //Handle swipe action for recyclerView and icon
        val myCallback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                //TODO Add swiped item to the cart
                Toast.makeText(applicationContext, "Swiped position : ${viewHolder.adapterPosition}", Toast.LENGTH_LONG)
                    .show()

                //TODO i should run animation on imageView here
                myAdapter.notifyDataSetChanged()
                // More code here
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {

                c.clipRect(
                    3f, viewHolder.itemView.top.toFloat(),
                    dX, viewHolder.itemView.bottom.toFloat()
                )
                val textMargin = 16

                addIcon.bounds = Rect(
                    textMargin,
                    viewHolder.itemView.top + textMargin,
                    textMargin + addIcon.intrinsicWidth,
                    viewHolder.itemView.top + addIcon.intrinsicHeight
                            + textMargin

                )
                addIcon.draw(c)

                super.onChildDraw(
                    c, recyclerView, viewHolder,
                    dX, dY, actionState, isCurrentlyActive
                )

            }


        }

        val myHelper = ItemTouchHelper(myCallback)
        myHelper.attachToRecyclerView(recyclerView)


    }


    override fun init() {
        recyclerView = findViewById(R.id.mainRecyclerView)
        addImageView = findViewById(R.id.MainAddIconImage)
        val manager: RecyclerView.LayoutManager
        manager = androidx.recyclerview.widget.GridLayoutManager(applicationContext, 2)
        recyclerView.layoutManager = manager
        mPresenter.getItems()


    }

    override fun showError(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
    }

    override fun loadData(ItemList: ArrayList<Item>) {
        myAdapter = MyAdapter(ItemList, this)
        recyclerView.adapter = myAdapter
        runSpotLight()
    }

    fun runSpotLight(){
        MaterialTapTargetPrompt.Builder(this@MainActivity)
            .setTarget(R.id.view)
            .setPrimaryText("You can swipe left to add the item to the cart")
            .setSecondaryText("Or you can delete it from the cart by swiping left !")
            .setPromptStateChangeListener { prompt, state ->
                if (state == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED) {
                    // User has pressed the prompt target
                }
            }
            .show()
    }

}
