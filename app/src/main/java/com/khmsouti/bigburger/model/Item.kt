package com.khmsouti.bigburger.model

import com.google.gson.annotations.SerializedName

 class Item{


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


fun getRef(): Int {
    return ref
}

fun setRef(ref: Int) {
    this.ref = ref
}

fun getTitle(): String? {
    return title
}

fun setTitle(title: String) {
    this.title = title
}

fun getDescription(): String? {
    return description
}

fun setDescription(description: String) {
    this.description = description
}

fun getPrice(): Int {
    return price
}

fun setPrice(price: Int) {
    this.price = price
}

fun getThumbnail(): String? {
    return thumbnail
}

fun setThumbnail(thumbnail: String) {
    this.thumbnail = thumbnail
}

 }