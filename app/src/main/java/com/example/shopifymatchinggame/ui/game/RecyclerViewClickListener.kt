package com.example.shopifymatchinggame.ui.game

import android.view.View
import com.example.shopifymatchinggame.model.Card

interface RecyclerViewClickListener {
    fun onRecyclerViewItemClick(view: View, card: Card)
}