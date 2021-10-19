package com.example.shopifymatchinggame.ui.game

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.shopifymatchinggame.R
import com.example.shopifymatchinggame.model.Card
import com.example.shopifymatchinggame.model.CardStatus
import com.example.shopifymatchinggame.model.Products
import com.example.shopifymatchinggame.ui.MainActivity
import kotlinx.android.synthetic.main.fragment_game.*
import nl.dionsegijn.konfetti.models.Shape
import nl.dionsegijn.konfetti.models.Size
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance


class GameFragment : Fragment(), RecyclerViewClickListener, KodeinAware {

    override val kodein by kodein()

    private lateinit var viewModel: GameViewModel
    private lateinit var adapter: CardItemAdapter
    private val factory: GameViewModelFactory by instance()
    private var pairList = arrayListOf<Card>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_game, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, factory).get(GameViewModel::class.java)

        // Subscribing to the liveData changes to execute the callback
        viewModel.subscribeToProductList(
            owner = viewLifecycleOwner,
            observer = Observer { cards ->
                val newDeck = viewModel.createCardDeck(cards)
                cardsRecyclerView.also {
                    it.layoutManager = GridLayoutManager(requireContext(), viewModel.gridLayoutColumn())
                    it.setHasFixedSize(true)
                    it.adapter = initAdapter(newDeck, this)
                }
            }
        )

        viewModel.subscribeToScore(
            owner = viewLifecycleOwner,
            observer = Observer { score ->
                scoreId.text = getString(
                    R.string.cards_found,
                    score.toString(),
                    viewModel.gridSize.toString()
                )
            }
        )

        homeButton.setOnClickListener {
            this.startActivity(Intent(context, MainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION))
        }
    }

    override fun onRecyclerViewItemClick(view: View, card: Card) {
        when (view.id) {
            R.id.cardId -> {
                if (card.cardStatus == CardStatus.MATCHED)
                    return
                else
                    flipCard(card)
            }
        }
    }

    private fun initAdapter(
        cards: List<Products>,
        listener: RecyclerViewClickListener
    ): CardItemAdapter {
        adapter = CardItemAdapter(
            cards,
            listener,
            viewModel
        )
        adapter.setHasStableIds(true)
        return adapter
    }

    private fun flipCard(card: Card) {
        pairList.add(card)
        if (pairList.size == 1) {
            updateCardStatus(card)
        } else if (pairList.isNotEmpty() && pairList.size != viewModel.cardMatchesNumber) {
            updateCardStatus(card)
            if (viewModel.verifyMatches(pairList)) {
                return
            } else {
                noMatch()
            }
        } else if (pairList.size == viewModel.cardMatchesNumber) {
            updateCardStatus(card)
            if (viewModel.verifyMatches(pairList)) {
                pairList.forEach {
                    it.cardStatus = CardStatus.MATCHED
                }
                pairList.clear()
                viewModel.updateScore()

                if (viewModel.score.value == viewModel.gridSize) {
                    showWinDialog()
                }
            } else {
                noMatch()
            }
        }
    }

    private fun updateCardStatus(card: Card) {
        if (card.cardStatus == CardStatus.FACE_DOWN) {
            card.cardStatus = CardStatus.FACE_UP
            adapter.notifyDataSetChanged()
        }
    }

    private fun noMatch() {
        view?.postDelayed({
                viewModel.resetCards(pairList)
                adapter.notifyDataSetChanged()
                pairList.clear()
            },
            500
        )
    }

    private fun showWinDialog() {
        val dialog = context?.let { Dialog(it) }.also {
            it?.setCanceledOnTouchOutside(false)
            it?.setContentView(R.layout.win_dialog)
            it?.show()
        }

        val returnHomeButton = dialog?.findViewById<Button>(R.id.returnHomeButton)
        returnHomeButton?.setOnClickListener {
            this.startActivity(Intent(context, MainActivity::class.java))
        }

        viewKonfetti!!.build()
            .addColors(Color.RED, Color.GREEN, Color.MAGENTA)
            .setDirection(0.0, 359.0)
            .setSpeed(5f)
            .setFadeOutEnabled(true)
            .setTimeToLive(5000L)
            .addShapes(Shape.Square, Shape.Circle)
            .addSizes(Size(12))
            .setPosition(-50f, viewKonfetti.width + 50f, -50f, -50f)
            .streamFor(400, 5000L)
    }
}

