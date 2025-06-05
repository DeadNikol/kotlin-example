package com.example.myapplication

import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.databinding.ActivityNotificationBinding

class NotificationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNotificationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Наконец-то украденный из интеренета код. Хоть я что-то и писал в манифесте касательно разрешений на уведомления, разрешения у меня никто не спрашивал, как следствие они и не отправляются. Теперь разрешение спрашивается
        askForPermissionForSendNotifications()

        val calendar: Calendar = Calendar.getInstance() //Чисто объект календаря

        binding = ActivityNotificationBinding.inflate(layoutInflater)

        binding.btnToSimpleNotification.setOnClickListener { //Кнопка простого уведомления
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                createNotificationChannelForSimpleNotification()
                showSimpleNotification()
            } else{
                showSimpleNotification()
            }
            Toast.makeText(this, "Нажато", Toast.LENGTH_SHORT).show()
        }

        binding.btnToDatedNotification.setOnClickListener { //Кнопка для уведомления по рассписанию
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                createDatePicker(calendar)
                createNotificationChannelForNotificationAtDate()
            } else
                createDatePicker(calendar)
        }

        setContentView(binding.root)
    }

    //Функция создания канала уведомлений для простого уведомления
    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannelForSimpleNotification(){
        val notificationManager = this.getSystemService(NOTIFICATION_SERVICE) as NotificationManager // Класс для уведомления пользователя о событиях, что происходят
        val name = "channelID1" // имя канала
        val desc = "A description of the Channel" // Описание канала
        val importance = NotificationManager.IMPORTANCE_DEFAULT //Степень важности уведомления
        //Степень важности можно задавать только единожды при создании канала
        val channel = NotificationChannel(name, name, importance) //Объявление канала уведомлений
        channel.description = desc //Добавление описания
        notificationManager.createNotificationChannel(channel) //Создание канала уведомлений, на который можно отправить уведомление
    }

    private fun showSimpleNotification(){  //Функция для создания простого уведомления
        val intent1 = Intent(this, FirstDemoActivity::class.java).apply{ //Намерение входа в приложение
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK //Определить откуда мы заходим: из приложения или из вне
        }
        //Пендинг нужен для того, что бы использовать intent с задержкой, потому что обычный передать нельзя)
        val pedningIntent = PendingIntent.getActivity(this, 0, intent1, PendingIntent.FLAG_IMMUTABLE)//Создание Пендинга для входа в приложение
        val notification = NotificationCompat.Builder(this, "channelID1") //СОздание уведомления
            .setSmallIcon(R.drawable.ic_launcher_foreground) //Иконка
            .setContentTitle("Заголовок 1") //Заголовок
            .setContentText("Текст 1") //Текст уведомления
            .setContentIntent(pedningIntent) //действие при нажатии на уведомление
            .setAutoCancel(true) //Закрытие при нажатии на уведомление
            .build()

        val manager = this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(1, notification) //Отправка уведомления в канал
        //Поскольку id один и тот же, то и уведомление одно и то же, но стоит его поменять, как все уведомления потекут рекой
    }

    private fun createDatePicker(calendar: Calendar){ //Выбор даты
        DatePickerDialog(
            this,
            {_: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth ->
                calendar.set(mYear, mMonth, mDayOfMonth)
                createTimePicker(calendar)
            },
            calendar[Calendar.YEAR],
            calendar[Calendar.MONTH],
            calendar[Calendar.DAY_OF_MONTH]
        ).show()
    }
    private fun createTimePicker(calendar: Calendar){
        TimePickerDialog(
            this,
            {_: TimePicker, mHour: Int, mMinute:Int ->
                calendar.set(Calendar.HOUR_OF_DAY, mHour)
                calendar.set(Calendar.MINUTE, mMinute)
                createNotificationAtDate(calendar)
            },
            calendar[Calendar.HOUR_OF_DAY],
            calendar[Calendar.MINUTE],
            true
        ).show()
    }

    private fun createNotificationAtDate(calendar: Calendar){
        val intent = Intent(this, Notification::class.java)
        val alarmManager = this.getSystemService(ALARM_SERVICE) as AlarmManager //Служба системных сигналов, что бы достать уведомление из памяти
        val pendingIntent = //Широковещательный сигнал
            PendingIntent.getBroadcast(
                this,
                0,
                intent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            pendingIntent
        )
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannelForNotificationAtDate(){//Логика такая же, как и для простого
        val notificationManager = this.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val name = "channelID2"
        val desc = "A Description for the channel 2"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel1 = NotificationChannel(name, name, importance)
        channel1.description = desc
        notificationManager.createNotificationChannel(channel1)
    }

    private fun askForPermissionForSendNotifications(){
        val REQUEST_CODE_POST_NOTIFICATIONS = 1 //Украденый из интернета код, проверяющий разрешение на отправку уведомлений
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), REQUEST_CODE_POST_NOTIFICATIONS)
            }
        }
    }
}