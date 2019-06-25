package com.khmsouti.bigburger.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

//Data type
//It present the data that comes from json
open class Item() : Parcelable {

    @SerializedName("ref")
    private var ref: Int = 0

    @SerializedName("title")
    private var title: String? = null

    @SerializedName("description")
    private var description: String? = null

    @SerializedName("price")
    private var price: Int = 0

    @SerializedName("thumbnail")
    private var thumbnail: String? = null

    constructor(parcel: Parcel) : this() {
        ref = parcel.readInt()
        title = parcel.readString()
        description = parcel.readString()
        price = parcel.readInt()
        thumbnail = parcel.readString()
    }


    fun getRef(): Int {
        return ref
    }


    fun getTitle(): String? {
        return title
    }

    fun getDescription(): String? {
        return description
    }

    fun getPrice(): Int {
        return price
    }

    fun getThumbnail(): String? {
        return thumbnail
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(ref)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeInt(price)
        parcel.writeString(thumbnail)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Item> {
        override fun createFromParcel(parcel: Parcel): Item {
            return Item(parcel)
        }

        override fun newArray(size: Int): Array<Item?> {
            return arrayOfNulls(size)
        }
    }

}