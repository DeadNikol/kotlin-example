package com.example.myapplication

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityBroadCastReceiverBinding

class MyReceiver: BroadcastReceiver() { //Этот класс наследуется от абстрактного класса, нужен для приёмя широковещательных сообщений
    //Его даже в манифесте нужно прописать
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent!!.getBooleanExtra("state", false)){ //При измении режима полёта выполняются следующие функции
            Toast.makeText(context, intent.getBooleanExtra("state", false).toString(), Toast.LENGTH_SHORT).show()

        }else{
            Toast.makeText(context,"Ошибочка вышла", Toast.LENGTH_SHORT).show()
        }
    }

}


class BroadCastReceiverActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBroadCastReceiverBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityBroadCastReceiverBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val receiver = MyReceiver() //Объявление приёмника
        val filter = IntentFilter("android.intent.action.AIRPLANE_MODE").apply {
            //Тут подключаются подписки приёмника

        }
        registerReceiver(receiver, filter) //Регистрация приёмника, что бы тот наконец работал
        //unregisterReceiver(receiver) //Если вдруг нам больше не нужна подписка, что бы тратить меньше заряда батареии


        var count = 0
        with(binding) {
            btnToClick.setOnClickListener { it ->
                (it as Button).text = "Нажатий: " + count++
            }
            btnToClose.setOnClickListener {
                finish()
            }
            //Это говно не работает, я не могу получать данные из других приложений по неизвестной причине
            btnToAdv.setOnClickListener { it ->//Отправка сообщений другим приложениям
                (it as Button).text = "Нажатий: " + count++

                val intent = Intent().also { intent -> //Создаём намерение
                    intent.action = "pipa" //По этому назавнию можно будет подписаться на рассылку
                    intent.putExtra("hot_news", etAdv.text.toString()) //Сама по себе информация
                }
                sendBroadcast(intent) //Отправка рассылки всем желающим

            }
        }
    }
}
