package com.example.shopifymatchinggame.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.shopifymatchinggame.R
import com.example.shopifymatchinggame.ui.game.GameFragment
import com.example.shopifymatchinggame.ui.settings.SettingsFragment
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        startButton.setOnClickListener {
            val newGameFragment =
                GameFragment()
            val fragmentTransaction= parentFragmentManager.beginTransaction()
                .also {
                    it.replace(R.id.fragmentHolder, newGameFragment)
                    it.addToBackStack(null)
                    it.commit()
                }
        }

        settingsButton.setOnClickListener {
            val newSettingsFragment =
                SettingsFragment()
            val fragmentTransaction = parentFragmentManager.beginTransaction()
                .also {
                    it.replace(R.id.fragmentHolder, newSettingsFragment)
                    it.addToBackStack(null)
                    it.commit()
                }

        }
    }
}