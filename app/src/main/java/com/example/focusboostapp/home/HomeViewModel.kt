package com.example.focusboostapp.home

import android.app.Application
import android.content.Context
import android.os.CountDownTimer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.focusboostapp.util.Settings
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class HomeViewModel (application: Application): AndroidViewModel(application) {

    lateinit var AppSettings: Settings
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var startTimestamp: String


    //--------------------LiveData---------------------
    val _hms = MutableLiveData<String>("00:00:00")
    val hms: LiveData<String>
        get() = _hms

    val _progressBarValue = MutableLiveData<Float>(0F)
    val progressBarValue: LiveData<Float>
        get() = _progressBarValue

    val _isRunning = MutableLiveData<Boolean>(false)
    val isRunning: LiveData<Boolean>
        get() = _isRunning
    //--------------------------------------------------

    //----------------------Timer-----------------------
    lateinit var timer: CountDownTimer
    //--------------------------------------------------

    fun init(context: Context) {
        /*settings = SharedPreferencesUtil(context)
        settingsHours = settings.getSettingsIntValue("timer_Hours")
        settingsMinutes = settings.getSettingsIntValue("timer_Minutes")
        settingseconds = settings.getSettingsIntValue("timer_Seconds")
        setHmsString(settingsHours.toLong(), settingsMinutes.toLong(), settingseconds.toLong())
        _progressBarValue.value = 100F*/
        AppSettings = Settings(context)
        AppSettings.updateSettings(context)
        setHmsString(AppSettings.settingsHours.toLong(), AppSettings.settingsMinutes.toLong(), AppSettings.settingseconds.toLong())
        _progressBarValue.value = 100F
    }

    fun getSettingsMillis() : Long{
        return ((AppSettings.settingsHours*3600000L)+(AppSettings.settingsMinutes*60000L)+(AppSettings.settingseconds*1000L))
    }

    fun startTimer(time: Long = 60000, countDownInterval: Long = 10) {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        startTimestamp = LocalDateTime.now().format(formatter).toString()
        timer = object : CountDownTimer(time , countDownInterval) {
            override fun onTick(millisUntilFinished: Long) {
                var millis: Long = millisUntilFinished
                var hMillis: Long = millis/3600000
                var mMillis: Long = (millis/60000)-(hMillis*60)
                var sMillis: Long = (millis/1000)%60

                setHmsString(hMillis, mMillis,sMillis)
                _progressBarValue.value = ((millis*100.0)/time).toFloat()

            }
            override fun onFinish() {
                setHmsString(AppSettings.settingsHours.toLong(), AppSettings.settingsMinutes.toLong(),AppSettings.settingseconds.toLong())
                _progressBarValue.value = 100F
                _isRunning.value = false

                saveSessionStats(true, startTimestamp)

            }
        }
        timer.start()
        _isRunning.value = true
    }
    fun stopTimer() {
        timer.cancel()
        _isRunning.value = false
        setHmsString(AppSettings.settingsHours.toLong(), AppSettings.settingsMinutes.toLong(),AppSettings.settingseconds.toLong())
        _progressBarValue.value = 100F

        saveSessionStats(false, startTimestamp)
    }

    private fun setHmsString(hours: Long, minutes: Long, seconds: Long){
        _hms.value = String.format ("%02d:%02d:%02d", hours, minutes, seconds)
    }

    private fun saveSessionStats(completedSession: Boolean, startTimestamp: String){
        firebaseAuth = FirebaseAuth.getInstance()
        val currentSessionUser = firebaseAuth.currentUser?.email.toString()

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        val current = LocalDateTime.now().format(formatter)

        val statsDatabase = FirebaseFirestore.getInstance()

        val currentSessionStats = hashMapOf<String, Any>(
            "user" to currentSessionUser,
            "sessionLengthHours" to AppSettings.settingsHours,
            "sessionLengthMinutes" to AppSettings.settingsMinutes,
            "sessionLengthSeconds" to AppSettings.settingseconds,
            "startDateTime" to startTimestamp,
            "stopDateTime" to current.toString(),
            "completedSession" to completedSession,
            "DND" to AppSettings.settingsDND,
            "immersiveMode" to AppSettings.settingsImmersiveMode
        )

        statsDatabase.collection("sessions")
            .add(currentSessionStats)

    }

}