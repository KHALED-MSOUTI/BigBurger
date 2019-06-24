package com.khmsouti.bigburger

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.khmsouti.bigburger.adapter.CartActivityRVAdapter
import com.khmsouti.bigburger.model.Item
import com.khmsouti.bigburger.presenter.CartPresenter
import com.khmsouti.bigburger.utils.SwipeCallback
import kotlinx.android.synthetic.main.cart_bottom_sheet.*

class CartActivity : AppCompatActivity(), CartActivityContract.View {
    private lateinit var mPresenter: CartPresenter
    private lateinit var intentArrayList: ArrayList<Item>
    private lateinit var cartRecyclerView: RecyclerView
    private lateinit var cartActivityRVAdapter: CartActivityRVAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        mPresenter = CartPresenter(this)
        mPresenter.Start()

    }


    override fun init() {
        cartRecyclerView = findViewById(R.id.cartRecyclerView)
        val manager: RecyclerView.LayoutManager
        manager = LinearLayoutManager(applicationContext)
        cartRecyclerView.layoutManager = manager
        mPresenter.fillData()
        val bottomSheetBehavior = BottomSheetBehavior.from(bottom_sheet_layout)
        bottomSheetBehavior.isHideable = false
        initSwipe()
    }

    override fun onBackPressed() {
        val intent = Intent(applicationContext, MainActivity::class.java)
        intent.putParcelableArrayListExtra("newTag", intentArrayList)
        startActivity(intent)

    }

    override fun loadData() {
        intent?.let {
            intentArrayList = intent.getParcelableArrayListExtra(getString(R.string.INTENT_TAG_TO_CART_ACTIVITY))
        }
        cartActivityRVAdapter = CartActivityRVAdapter(intentArrayList)
        cartRecyclerView.adapter = cartActivityRVAdapter
        setAmount()
    }

    private fun initSwipe() {
        val swipeHandler = object : SwipeCallback(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                if (direction == ItemTouchHelper.LEFT) {
                    deleteFromCart(viewHolder.adapterPosition)
                    setAmount()
                } else {
                    addToCart(viewHolder.adapterPosition)
                    setAmount()
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(cartRecyclerView)
    }

    private fun deleteFromCart(position: Int) {
        intentArrayList.removeAt(position)
        cartActivityRVAdapter.notifyDataSetChanged()
    }

    private fun addToCart(position: Int) {
        intentArrayList.add(intentArrayList.size, intentArrayList[position])
        cartActivityRVAdapter.notifyDataSetChanged()
    }


    private fun setAmount() {
        var totalAmount = 0.0f
        for (i in intentArrayList) {
            totalAmount += getPrice((i.getPrice()).toString())
        }
        val taxes = (totalAmount * 18) / 100
        TaxRate.text = getString(R.string.TAX_RATE)
        val textTaxAmount = "$taxes ₺"
        TaxAmount.text = textTaxAmount
        val textCartAmount = "${totalAmount - taxes} ₺"
        cartItemAmount.text = textCartAmount
        val textAmount = "$totalAmount ₺"
        TotalAmount.text = textAmount


    }

    private fun getPrice(oldPrice: String): Float {
        var price = oldPrice.substring(0, oldPrice.length - 2) + "." + oldPrice.substring(oldPrice.length - 2)
        if (price.length == 3) price = "0$price"
        return price.toFloat()
    }

}
