package com.example.myapplication

import android.app.IntentService
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

//Это типа сервис. У него нет UI, но он выполняется. В теории он делает это в отдельном потоке из-за того, что наследуется от класса IntentService()
class MyIntentService : IntentService("MyIntentService") {

    private val TAG = "ServiceExample"

    override fun onHandleIntent(arg0: Intent?) {
        Log.i(TAG, "Intent Service started")
    }
}

class BoundService: Service(){ //Этот сервис должен возвращать время системы
    private val myBinder = MyLocalBinder()

    override fun onBind(intent: Intent?): IBinder? {
        return myBinder
    }

    inner class MyLocalBinder: Binder(){ //Класс, возвращающий сервис
        fun getService() : BoundService {
            return this@BoundService
        }
    }

    fun getCurrentTime():String{ //Типа возвращем время в виде строке, хоть я ничего и не понимаю
        val dateformat = SimpleDateFormat("HH:mm:ss MM/dd/yyyy", Locale.US)
        return dateformat.format(Date())
    }
}