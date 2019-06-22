package com.khmsouti.bigburger.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.khmsouti.bigburger.model.Item
import com.squareup.picasso.Picasso


class MyAdapter(private var itemList: ArrayList<Item>, var context: Context) :
    androidx.recyclerview.widget.RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            com.khmsouti.bigburger.R.layout.main_recyclerview_item,
            parent,
            false
        )
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val displayMetrics = context.resources.displayMetrics
        val dpWidth = displayMetrics.widthPixels
        holder.layout.layoutParams.width = (dpWidth/2)

        holder.textViewTitle.text = itemList[position].getTitle()
        holder.textViewPrice.text = getFinePrice(itemList[position].getPrice())
        holder.textViewInfo.text = itemList[position].getDescription()
        holder.loadImage(itemList[position].getThumbnail().toString())
        holder.image.contentDescription = itemList[position].getTitle()
    }

    inner class MyViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
        var layout: ConstraintLayout = itemView.findViewById(com.khmsouti.bigburger.R.id.ItemRootLayout)
        var image: ImageView = itemView.findViewById(com.khmsouti.bigburger.R.id.mainItemImage)
        var textViewTitle: TextView = itemView.findViewById(com.khmsouti.bigburger.R.id.mainItemTitle)
        var textViewPrice: TextView = itemView.findViewById(com.khmsouti.bigburger.R.id.mainItemPrice)
        var textViewInfo: TextView = itemView.findViewById(com.khmsouti.bigburger.R.id.mainItemInfo)

        fun loadImage(url: String) {
            //TODO handle no image error !!
            Picasso.get().load(url).into(image)

        }
    }

    private fun getFinePrice(cents: Int): String {
        var fPrice: String = cents.toString()
        fPrice = fPrice.substring(0, fPrice.length - 2) + "." + fPrice.substring(fPrice.length - 2)
        if (fPrice.length == 3) {
            fPrice = "0$fPrice"
        }

        return fPrice+"₺"
    }
}