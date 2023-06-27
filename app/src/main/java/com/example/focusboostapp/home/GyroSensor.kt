package com.example.focusboostapp.home

import android.content.Context
import android.content.Context.SENSOR_SERVICE
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import java.time.LocalDateTime
import kotlin.math.absoluteValue

class GyroSensor : SensorEventListener {

    lateinit var userDistractions : ArrayList<LocalDateTime>
    private lateinit var sensorManager: SensorManager
    var counter = 0


    fun startSensor(context: Context){
        userDistractions = ArrayList()
        // Create the sensor manager
        sensorManager = context.getSystemService(SENSOR_SERVICE) as SensorManager

        // Specify the sensor you want to listen to
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
        // Checks for the sensor we have registered
        if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
            //Log.d("Main", "onSensorChanged: sides ${event.values[0]} front/back ${event.values[1]} ")

            // Sides = Tilting phone left(10) and right(-10)
            val sides = event.values[0]
            Log.i("SIDES", event.values[0].toString())


            // Up/Down = Tilting phone up(10), flat (0), upside-down(-10)
            val upDown = event.values[1]
            Log.i("UP/DOWN", event.values[1].toString())

            if( sides.absoluteValue >.5f || upDown.absoluteValue >1.5f) {
                counter++
                Log.i("LIFT-UP", "Smartphone touched!")

                if (counter > 10) {
                    userDistractions.add(LocalDateTime.now())
                    counter = 0
                }
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        return
    }
}