package com.example.myapplication

import android.hardware.SensorManager
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityInnerSensorsBinding

class InnerSensorsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInnerSensorsBinding
    lateinit var sm: SensorManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityInnerSensorsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sm = getSystemService(SENSOR_SERVICE) as SensorManager //Создаём переменую менеджера сенсоров
        val sensorList = sm.getSensorList(-1) //Получаем все сенсоры в виде списка
        var sensorName = ArrayList<String>() //Создаём переменную под названия сенсоров
        sensorList.forEach {it ->
            sensorName.add(it.name) //Закидываем имена в список
        }
        var adapterName = ArrayAdapter(this@InnerSensorsActivity, android.R.layout.simple_list_item_1, sensorName) //Создаём адаптер для ListView
        binding.lvServices.adapter = adapterName //Связываем адаптер с ListView

        binding.lvServices.onItemClickListener = AdapterView.OnItemClickListener { adapt, view, index, id -> //При клике на элемент списка...
            val rSensor = sensorList[index] //...создаём пременную конкретного сенсора,
            var sensor_info = "name: " + rSensor.name + //Тырим оттуда все значения
                    "\ntype: " + rSensor.type +
                    "\npower: " + rSensor.power +
                    "\nversion: " + rSensor.version +
                    "\nvendor: " + rSensor.vendor +
                    "\nresolution: " + rSensor.resolution +
                    "\nDynamic: " + rSensor.isDynamicSensor +
                    "\nWakeUp: " + rSensor.isWakeUpSensor

            Toast.makeText(applicationContext,sensor_info,Toast.LENGTH_LONG).show() //И показываем их в тосте
        }
        binding.btnToGetServices.setOnClickListener {
            Toast.makeText(this, adapterName.toString(), Toast.LENGTH_SHORT).show()
        }


    }
}