package com.example.shopifymatchinggame.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.shopifymatchinggame.model.Image

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, image: Image) {
    Glide.with(view)
        .load(image.imageSource)
        .into(view)
}