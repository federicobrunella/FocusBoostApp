package com.example.focusboostapp.util

import android.content.Context

class Settings (context: Context) {

    private lateinit var settings: SharedPreferencesUtil

    var settingsHours: Int = 0
    var settingsMinutes: Int = 0
    var settingseconds: Int = 0

    var settingsDND: Boolean = false
    var settingsImmersiveMode: Boolean = false


    fun updateSettings(context: Context) {
        settings = SharedPreferencesUtil(context)
        settingsHours = settings.getIntValue("timer_Hours")
        settingsMinutes = settings.getIntValue("timer_Minutes")
        settingseconds = settings.getIntValue("timer_Seconds")

        settingsDND = settings.getBoolValue("DND")
        settingsImmersiveMode = settings.getBoolValue("ImmersiveMode")

    }

    fun saveSettings(context: Context) {
        settings = SharedPreferencesUtil(context)
        settings.setIntValue("timer_Hours", settingsHours)
        settings.setIntValue("timer_Minutes", settingsMinutes)
        settings.setIntValue("timer_Seconds", settingseconds)

        settings.setBoolValue("DND", settingsDND)
        settings.setBoolValue("ImmersiveMode",settingsImmersiveMode)

    }
}