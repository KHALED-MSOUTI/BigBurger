package com.khmsouti.bigburger.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.khmsouti.bigburger.R
import com.khmsouti.bigburger.model.Item
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso


class MainActivityRVAdapter(private var itemList: ArrayList<Item>, private var context: Context) :
    androidx.recyclerview.widget.RecyclerView.Adapter<MainActivityRVAdapter.MyViewHolder>() {
    //Inflate list row layout

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.main_recyclerview_item,
            parent,
            false
        )
        return MyViewHolder(view)
    }


    //Get numbers of items to be shown
    override fun getItemCount(): Int {
        return itemList.size
    }

    //Data will get it's place in the items to be visible to the user
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val displayMetrics = context.resources.displayMetrics
        val dpWidth = displayMetrics.widthPixels
        holder.layout.layoutParams.width = (dpWidth / 2)

        holder.textViewTitle.text = itemList[position].getTitle()
        holder.textViewPrice.text = getFinePrice(itemList[position].getPrice())
        holder.textViewInfo.text = itemList[position].getDescription()
        holder.loadImage(itemList[position].getThumbnail().toString())
        holder.image.contentDescription = itemList[position].getTitle()
    }

    //viewHolder will handle view's initializing
    inner class MyViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
        var layout: ConstraintLayout = itemView.findViewById(R.id.ItemRootLayout)
        var image: ImageView = itemView.findViewById(R.id.mainItemImage)
        var textViewTitle: TextView = itemView.findViewById(R.id.mainItemTitle)
        var textViewPrice: TextView = itemView.findViewById(R.id.mainItemPrice)
        var textViewInfo: TextView = itemView.findViewById(R.id.mainItemInfo)

        //Handling Picasso load image process
        fun loadImage(url: String) {
            Picasso.get().load(url).error(R.drawable.ic_no_image_white_96dp).into(image, object : Callback {
                override fun onError(e: Exception?) {
                    //wil load the image at "R.drawable.error" instead
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