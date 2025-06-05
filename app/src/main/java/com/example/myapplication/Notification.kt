package com.example.myapplication

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat

class Notification: BroadcastReceiver() {//Класс для NotificationActivity
    override fun onReceive(context: Context, intent: Intent) {
        val intent1 = Intent(context, FirstDemoActivity::class.java).apply{
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent =
            PendingIntent.getActivity(context, 0, intent1, PendingIntent.FLAG_IMMUTABLE)
        val notification = NotificationCompat.Builder(context, "channelID2")
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setContentTitle("Заголовок 2")
            .setContentText("Текст 2")
            .setDefaults(Notification.DEFAULT_ALL)
            .setContentIntent(pendingIntent)
            .setAutoCancel(false)
            .build()
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(2, notification)
    }
}