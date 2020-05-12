package com.example.shopifymatchinggame.ui.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shopifymatchinggame.network.ProductsRepository

class GameViewModelFactory(
    private val repository: ProductsRepository
): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return GameViewModel(repository) as T
    }
}