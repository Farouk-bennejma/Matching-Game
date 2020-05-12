package com.example.shopifymatchinggame.model

import com.google.gson.annotations.SerializedName

data class ProductList (
    @SerializedName("products") val products: List<Products>
)
