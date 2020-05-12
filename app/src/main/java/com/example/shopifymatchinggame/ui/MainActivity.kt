package com.example.shopifymatchinggame.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.shopifymatchinggame.R

class MainActivity : AppCompatActivity() {

    private val homeFragment = HomeFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragmentHolder, homeFragment, "homeFragment")
            .commit()

        this.supportActionBar?.hide()
    }
}
