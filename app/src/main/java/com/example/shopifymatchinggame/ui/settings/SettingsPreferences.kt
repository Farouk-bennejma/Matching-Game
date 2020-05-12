package com.example.shopifymatchinggame.ui.settings

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

class SettingsPreferences(context: Context) {

    private val appContext = context.applicationContext
    private val sharedPreferences: SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(appContext)

    fun getCardMatchesNumber(): Int {
        return sharedPreferences.getString("cardMatchesNumber", "")!!.toInt()
    }

    fun getGridSize(): Int {
        return sharedPreferences.getString("gridSizeNumber", "")!!.toInt()
    }

}