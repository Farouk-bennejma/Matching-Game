package com.example.shopifymatchinggame

import android.app.Application
import com.example.shopifymatchinggame.network.ProductsRepository
import com.example.shopifymatchinggame.ui.game.GameViewModelFactory
import com.example.shopifymatchinggame.ui.settings.SettingsPreferences
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

class ShopifyMatchingGame: Application(), KodeinAware {

    override val kodein: Kodein = Kodein.lazy {
        import(androidXModule(this@ShopifyMatchingGame))

        bind() from singleton {
            SettingsPreferences(
                instance()
            )
        }
        bind() from singleton { ProductsRepository(instance()) }
        bind() from singleton { GameViewModelFactory(instance()) }
    }

}