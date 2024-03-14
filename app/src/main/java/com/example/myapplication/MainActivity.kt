package com.example.myapplication

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity(), SensorEventListener {

    lateinit var sensorManager: SensorManager
    lateinit var lightText: TextView
    lateinit var pressText: TextView
    lateinit var tempText: TextView
    lateinit var imageView: ImageView
    var lightSensor: Sensor? = null
    var pressSensor: Sensor? = null
    var rotateSensor: Sensor? = null
    var tempSensor: Sensor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        val list: List<Sensor> = sensorManager.getSensorList(Sensor.TYPE_ALL)
        println("Size=${list.size}")
        println(list.joinToString("\n"))

        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
        if(lightSensor != null) {
            sensorManager.registerListener(this,lightSensor,2)
        }
        lightText = findViewById(R.id.lightText)

        pressSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE)
        if(pressSensor != null) {
            sensorManager.registerListener(this,pressSensor,2)
        }
        pressText = findViewById(R.id.pressureText)

        tempSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)
        if(tempSensor != null) {
            sensorManager.registerListener(this,tempSensor,2)
        }
        tempText = findViewById(R.id.tempText)

        rotateSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
        if(rotateSensor != null) {
            sensorManager.registerListener(this,rotateSensor,2)
        }
        imageView = findViewById(R.id.imageView)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if(lightSensor == null) {
            lightText.text = "No light sensor!"
        } else if(Sensor.TYPE_LIGHT == event!!.sensor.type) {
            lightText.text = "Light: ${event.values[0]} "
        }
        if(pressSensor == null) {
            pressText.text = "No press sensor!"
        } else if(Sensor.TYPE_PRESSURE == event!!.sensor.type) {
            pressText.text = "Pressure: ${event.values[0]} "
        }
        if(tempSensor == null) {
            tempText.text = "No temp sensor!"
        } else if(Sensor.TYPE_AMBIENT_TEMPERATURE == event!!.sensor.type) {
            tempText.text = "Temp: ${event.values[0]} "
        }
        if(Sensor.TYPE_GYROSCOPE == event!!.sensor.type) {
            imageView.rotationX = event.values[0]*10
            imageView.rotationY = event.values[1]*10
            imageView.rotation = event.values[2]*10
        }

    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }
}