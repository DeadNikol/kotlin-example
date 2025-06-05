package com.example.myapplication

import android.content.Context.SENSOR_SERVICE
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.databinding.ActivityEnvironmentalMonitoringBinding

class EnvironmentalMonitoringActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var binding: ActivityEnvironmentalMonitoringBinding

    val noSensor = "Датчик не установлен"
    var tSensor: Sensor? = null             //температура
    var lSensor: Sensor? = null             //свет
    var pSensor: Sensor? = null           //давление
    var hSensor: Sensor? = null           //влажность
    var rvSensor: Sensor? = null        //Наклон
    var prSensor: Sensor? = null         //приближение
    lateinit var sm: SensorManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityEnvironmentalMonitoringBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sm = getSystemService(SENSOR_SERVICE) as SensorManager
    }

    override fun onResume() {
        super.onResume()
        tSensor =
            sm.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE) //Получение датчика температуры вокруг
        if (tSensor != null) //Если таковой имеется
            sm.registerListener(this, tSensor, SensorManager.SENSOR_DELAY_GAME) //Регестрируем его
        //И так для каждого
        lSensor = sm.getDefaultSensor(Sensor.TYPE_LIGHT)
        if (lSensor != null)
            sm.registerListener(this, lSensor, SensorManager.SENSOR_DELAY_GAME)

        pSensor = sm.getDefaultSensor(Sensor.TYPE_PRESSURE)
        if (pSensor != null)
            sm.registerListener(this, pSensor, SensorManager.SENSOR_DELAY_GAME)

        hSensor = sm.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY)
        if (hSensor != null)
            sm.registerListener(this, hSensor, SensorManager.SENSOR_DELAY_GAME)

        rvSensor = sm.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
        if(rvSensor != null) sm.registerListener(this, rvSensor, SensorManager.SENSOR_DELAY_GAME)

        prSensor = sm.getDefaultSensor(Sensor.TYPE_PROXIMITY)
        if(prSensor != null) sm.registerListener(this,prSensor,SensorManager.SENSOR_DELAY_GAME)
    }

    override fun onPause() {
        super.onPause()
        sm.unregisterListener(this)
    } //Снимем регистрацию датчиков, что бы батарею не ело

    override fun onSensorChanged(event: SensorEvent?) {
        var h = 0f
        var t = 0f
        if (tSensor == null) binding.temperature.text = "ТЕМПЕРАТУРА: " + noSensor
        else if (event!!.sensor.type == tSensor!!.type) {
            t = event.values[0]; binding.temperature.text = "ТЕМПЕРАТУРА: " + t
        }

        if (lSensor == null) binding.light.text = "ОСВЕЩЁННОСТЬ: " + noSensor
        else if (event!!.sensor.type == lSensor!!.type) binding.light.text =
            "ОСВЕЩЁННОСТЬ: " + event.values[0]

        if (pSensor == null) binding.pressure.text = "АТМОСФЕРНОЕ ДАВЛЕНИЕ: " + noSensor
        else if (event!!.sensor.type == pSensor!!.type) binding.pressure.text =
            "АТМОСФЕРНОЕ ДАВЛЕНИЕ: " + event.values[0]

        if (hSensor == null) binding.humidity.text = "ОТНОСИТЕЛЬНАЯ ВЛАЖНОСТЬ: " + noSensor
        else if (event!!.sensor.type == hSensor!!.type) {
            h = event.values[0]; binding.humidity.text = "ОТНОСИТЕЛЬНАЯ ВЛАЖНОСТЬ: " + h
        }
        //Это расчёт точки росы
        var str = ""
        if (tSensor != null && hSensor != null) {
            var dewPoint =
                243.12 * (Math.log(h / 100.0) + 17.62 * t / (243.12 + t)) /
                        (17.62 - Math.log(h / 100.0) - 17.62 * t / (243.12 + t))
            str = "точка росы: " + dewPoint}
        else str = "нет возможности вычислить точку росы"
        binding.dewpoint.text = str

        if (event!!.sensor.type == rvSensor!!.type){
            binding.ivFive.rotation = event.values[1]*10
        }

        if(event!!.sensor.type == prSensor!!.type &&  //работает датчик приближения
            event.values[0] < 2)  {                                     // ближе 2 см находится некоторый предмет
            val alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION) //используем системный звук уведомлений
            val mp = MediaPlayer.create(applicationContext, alert)   // создаём звуковой объект
            mp.start()                                                                          // и запускаем его
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) { }

}