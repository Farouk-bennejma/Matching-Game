package com.example.shopifymatchinggame.ui.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.example.shopifymatchinggame.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings_main, rootKey)
    }
}