package com.example.focusboostapp.home

import android.content.Context
import android.content.Context.SENSOR_SERVICE
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import com.google.firebase.Timestamp
import java.time.LocalDateTime
import kotlin.math.absoluteValue

class GyroSensor : SensorEventListener {

    var userDistractions : ArrayList<Timestamp> = arrayListOf()
    private lateinit var sensorManager: SensorManager
    private  lateinit var context: Context
    var counter = 0

    fun startSensor(appContext: Context){
        context = appContext
        userDistractions = ArrayList()

        sensorManager = context.getSystemService(SENSOR_SERVICE) as SensorManager

        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)?.also { accelerometer ->
            sensorManager.registerListener(
                this,
                accelerometer,
                SensorManager.SENSOR_DELAY_NORMAL,
                SensorManager.SENSOR_DELAY_NORMAL
            )
        }
    }

    fun stopSensor(){
        sensorManager.unregisterListener(this)
    }


    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {

            val sides = event.values[0]
            Log.i("SIDES", event.values[0].toString())

            val upDown = event.values[1]
            Log.i("UP/DOWN", event.values[1].toString())

            if( sides.absoluteValue >1.5f || upDown.absoluteValue >1.5f) {
                counter++
                if (counter > 10) {
                    userDistractions.add(Timestamp.now())
                    counter = 0
                    Toast.makeText(context, "Stay Focused!", Toast.LENGTH_SHORT).show()
                    vibrate()
                }
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        return
    }

    private fun vibrate(){
        val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        vibrator.vibrate(VibrationEffect.createOneShot(300,
            VibrationEffect.DEFAULT_AMPLITUDE))
    }
}