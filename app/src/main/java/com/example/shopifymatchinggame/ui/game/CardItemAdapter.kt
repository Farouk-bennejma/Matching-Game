package com.example.shopifymatchinggame.ui.game

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.shopifymatchinggame.R
import com.example.shopifymatchinggame.databinding.CardItemBinding
import com.example.shopifymatchinggame.model.Products

class CardItemAdapter(
    private val products: List<Products>,
    private val listener: RecyclerViewClickListener,
    private val viewModel: GameViewModel
): RecyclerView.Adapter<CardItemAdapter.CardsViewHolder>() {

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        viewModel.setUpDeck(products)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardsViewHolder {
        return CardsViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.card_item,
                parent,
                false)
        )
    }

    override fun getItemCount(): Int {
        return viewModel.gridSize * viewModel.cardMatchesNumber
    }

    override fun onBindViewHolder(holder: CardsViewHolder, position: Int) {
        holder.binding.card = viewModel.cards[position]
        holder.binding.root.setOnClickListener {
            listener.onRecyclerViewItemClick(holder.binding.root, viewModel.cards[position])
        }

    }

    inner class CardsViewHolder(
        val binding: CardItemBinding
    ) : RecyclerView.ViewHolder(binding.root)
}

