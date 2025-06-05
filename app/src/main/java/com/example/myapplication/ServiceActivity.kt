package com.example.myapplication

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.databinding.ActivityServiceBinding

class ServiceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityServiceBinding
    var myService: BoundService? = null //Объект сервиса
    var isBound = false //Связан ли я с этим сервисом

    fun showTime(){ //Собвстенно метод, ради которого всё и затевалось
        val currentTime = myService?.getCurrentTime()
        binding.tvTime.text = currentTime
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityServiceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //Подписываемся на сервис со временем
        val intent = Intent(this@ServiceActivity, BoundService::class.java)
        bindService(intent, myConnection, Context.BIND_AUTO_CREATE) //Что бы это ни значило

        with(binding){
            btnSimpleService.setOnClickListener{
                startService(Intent(this@ServiceActivity, MyIntentService::class.java))
            }
            btnToTimeService.setOnClickListener{
                showTime() //Вызов ключевой функции
            }

        }
    }

    private val myConnection = object : ServiceConnection{ //что-то, что нужно для подписки на сервис
        override fun onServiceConnected(classname: ComponentName, service: IBinder?) {
            val binder = service as BoundService.MyLocalBinder
            myService = binder.getService() //Собственно сам сервис
            isBound = true //тут говорим, что подписались
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            isBound = false //Говорим, что отписались
        }
    }
}