package com.example.shopifymatchinggame.model

import com.google.gson.annotations.SerializedName

data class Products (
    @SerializedName("id") val productId: String,
    @SerializedName("title") val productTitle: String,
    @SerializedName("image") val image: Image
)
