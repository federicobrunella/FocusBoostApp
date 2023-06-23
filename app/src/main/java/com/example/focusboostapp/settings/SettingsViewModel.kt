package com.example.focusboostapp.settings

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.focusboostapp.util.Settings
import com.example.focusboostapp.util.SharedPreferencesUtil

class SettingsViewModel(application: Application): AndroidViewModel(application){

    //private lateinit var settings: SharedPreferencesUtil
    lateinit var AppSettings: Settings


    //--------------------Settings----------------------
    //var settingsHours: Int = 0
    //var settingsMinutes: Int = 0
    //var settingseconds: Int = 0
    //--------------------------------------------------

    //--------------------LiveData---------------------
    val _hmsSettings = MutableLiveData<String>("00:00:00")
    val hmsSettings: LiveData<String>
        get() = _hmsSettings
    //--------------------------------------------------

    fun init(context: Context) {
        /*settings = SharedPreferencesUtil(context)
        settingsHours = settings.getSettingsIntValue("timer_Hours")
        settingsMinutes = settings.getSettingsIntValue("timer_Minutes")
        settingseconds = settings.getSettingsIntValue("timer_Seconds")
        setHmsString(settingsHours, settingsMinutes, settingseconds)*/
        AppSettings = Settings(context)
        AppSettings.updateSettings(context)
        setHmsString(AppSettings.settingsHours, AppSettings.settingsMinutes, AppSettings.settingseconds)
    }
    //fun saveSettings(context: Context, hours: Int, minutes: Int, seconds: Int) {
    fun saveSettings(context: Context) {
        /*setHmsString(settingsHours, settingsMinutes, settingseconds)
        settings = SharedPreferencesUtil(context)
        settings.setIntValue("timer_Hours", hours)
        settings.setIntValue("timer_Minutes", minutes)
        settings.setIntValue("timer_Seconds", seconds)*/

        setHmsString(AppSettings.settingsHours, AppSettings.settingsMinutes, AppSettings.settingseconds)
        AppSettings.saveSettings(context)

    }

    private fun setHmsString(hours: Int, minutes: Int, seconds: Int){
        _hmsSettings.value = String.format ("%02d:%02d:%02d", hours, minutes, seconds)
    }

}