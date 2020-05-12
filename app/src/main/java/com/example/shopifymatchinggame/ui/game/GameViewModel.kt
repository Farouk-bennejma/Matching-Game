package com.example.shopifymatchinggame.ui.game

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shopifymatchinggame.model.Card
import com.example.shopifymatchinggame.model.CardStatus
import com.example.shopifymatchinggame.model.ProductList
import com.example.shopifymatchinggame.model.Products
import com.example.shopifymatchinggame.network.ProductsRepository

class GameViewModel(
    private val repository: ProductsRepository
) : ViewModel() {

    private var productList = MutableLiveData<ProductList>()
    private var deck = mutableListOf<Products>()
    val score = MutableLiveData<Int>()
    val cards = mutableListOf<Card>()

    val cardMatchesNumber = repository.getSettings().getCardMatchesNumber()
    val gridSize = repository.getSettings().getGridSize()

    init {
        score.value = 0
    }

    fun getProducts(): LiveData<ProductList> {
        productList = repository.getProducts() as MutableLiveData<ProductList>
        return productList
    }

    fun setUpDeck(products: List<Products>) {
        products.forEach {
            cards.add(Card(it, CardStatus.FACE_DOWN))
        }
    }

    fun createCardDeck(productList: ProductList): List<Products> {
        productList.products.forEach {
            deck.add(it)
        }

        deck = deck.subList(0, gridSize)
        for (x in 0 until 2)
            deck.addAll(deck)

        val deckSize = cardMatchesNumber * gridSize
        deck = deck.subList(0, deckSize)
        deck.shuffle()

        return deck
    }

    fun updateScore() {
        score.value = (score.value)?.plus(1)
    }

    fun verifyMatches(list: List<Card>): Boolean {
        var result = false
        list.forEach {
            result = list[0].products.image == it.products.image
        }
        return result
    }

    fun resetCards(cards: ArrayList<Card>){
        cards.forEach {
            if (it.cardStatus != CardStatus.MATCHED){
                it.cardStatus = CardStatus.FACE_DOWN
            }
        }
    }

    fun gridLayoutColumn(): Int {
        return when (cardMatchesNumber * gridSize) {
            20, 24, 28 -> 4
            30, 40 -> 5
            36, 42, 48 -> 6
            56 -> 7
            else -> 4
        }
    }
}