package com.example.focusboostapp.util

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesUtil (context: Context) {
    private val PREF_NAME = "settings"

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun getIntValue(key: String): Int {
        return sharedPreferences.getInt(key, 0)
    }

    fun getBoolValue(key: String): Boolean {
        return sharedPreferences.getBoolean(key, false)
    }

    fun setStringValue(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    fun setIntValue(key: String, value: Int) {
        sharedPreferences.edit().putInt(key, value).apply()
    }

    fun setBoolValue(key: String, value: Boolean) {
        sharedPreferences.edit().putBoolean(key, value).apply()
    }
}