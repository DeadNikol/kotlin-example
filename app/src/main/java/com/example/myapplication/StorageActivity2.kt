package com.example.myapplication

import android.content.Context
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.databinding.ActivityStorage1Binding
import com.example.myapplication.databinding.ActivityStorage2Binding

class StorageActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivityStorage2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStorage2Binding.inflate(layoutInflater)

        enableEdgeToEdge()
        setContentView(binding.root)

        //Создаём новое хранилище или обращаемся к старому
        val sharedPreferences = getSharedPreferences("user_preferences", Context.MODE_PRIVATE)
        //Снова тырим значения
        val CurrentUsername = sharedPreferences.getString("username", "")
        val CurrentIsDarkMode = sharedPreferences.getBoolean("dark_mode", false)


        with(binding)
        {
            //Задаём значения(До того, как пользователь их увидит
            et1.setText(CurrentUsername)
            sw1.isChecked = CurrentIsDarkMode
            //При нажатии на кнопку перезаписываем значения в хранилище
            btn1.setOnClickListener{
                //Создаём под них переменные
                val newUserName = et1.text.toString()
                val isDarkMode = sw1.isChecked

                //Создаём редактор
                val editor = sharedPreferences.edit()
                //Редактируем)
                editor.putString("username", newUserName)
                editor.putBoolean("dark_mode", isDarkMode)
                //Применяем изменения
                editor.apply()

                //Возвращаем Intent типа всё прошло по плану
                setResult(RESULT_OK)
                //Убиваем активность
                finish()
            }
        }
    }
}