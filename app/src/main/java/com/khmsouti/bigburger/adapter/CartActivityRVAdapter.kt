package com.khmsouti.bigburger.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.khmsouti.bigburger.R
import com.khmsouti.bigburger.model.Item
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class CartActivityRVAdapter(private var list: ArrayList<Item>) :
    RecyclerView.Adapter<CartActivityRVAdapter.CartViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        //Inflate list row layout
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.cart_recyclerview_item,
            parent,
            false
        )
        return CartViewHolder(view)
    }

    //Get numbers of items to be shown
    override fun getItemCount(): Int {
        return list.size
    }

    //Data will get it's place in the items to be visible to the user
    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.cartItemTitle.text = list[position].getTitle()
        holder.cartItemPrice.text = getFinePrice(list[position].getPrice())
        holder.cartItemDescription.text = list[position].getDescription()
        holder.loadImage(list[position].getThumbnail().toString())
        holder.cartItemImageView.contentDescription = list[position].getTitle()
    }

    //viewHolder will handle view's initializing
    inner class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var cartItemImageView: ImageView = itemView.findViewById(R.id.cartItemImageView)
        var cartItemTitle: TextView = itemView.findViewById(R.id.cartItemTitle)
        var cartItemPrice: TextView = itemView.findViewById(R.id.cartItemPrice)
        var cartItemDescription: TextView = itemView.findViewById(R.id.cartItemDescription)

        //Handling Picasso load image process
        fun loadImage(url: String) {
            Picasso.get().load(url).error(R.drawable.error).into(cartItemImageView, object : Callback {
                override fun onError(e: Exception?) {
                    //wil load the image R.drawable.error instead
                }

                override fun onSuccess() {
                }

            })
        }
    }

    //Convert price from cents (Int) "as it comes from json call" to String and add currency symbol.
    private fun getFinePrice(cents: Int): String {
        var fPrice: String = cents.toString()
        fPrice = fPrice.substring(0, fPrice.length - 2) + "." + fPrice.substring(fPrice.length - 2)
        if (fPrice.length == 3) {
            fPrice = "0$fPrice"
        }
        return fPrice + "₺"
    }

}