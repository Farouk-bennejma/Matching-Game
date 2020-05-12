package com.example.shopifymatchinggame.model

import com.google.gson.annotations.SerializedName

data class Image (
    @SerializedName("id") val imageId: String,
    @SerializedName("src") val imageSource: String
)
