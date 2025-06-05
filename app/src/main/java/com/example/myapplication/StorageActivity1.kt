package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.databinding.ActivityStorage1Binding

class StorageActivity1 : AppCompatActivity() {
    private lateinit var binding: ActivityStorage1Binding

    private val startForResult = //Это нужно проссто запомнить - для вызова intent с возвращаемым значением
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
            if (result.resultCode == RESULT_OK){
                updateUI()
                binding.ivPicture.setImageURI(result.data?.data)
            }
        }

    private fun updateUI() {
        //Создание нового или обращение к старому хранилищу
        val sharedPreferences = getSharedPreferences("user_preferences", Context.MODE_PRIVATE)
        //Берём значения по ключам
        val username = sharedPreferences.getString("username", "Guest")
        val isDarkMode = sharedPreferences.getBoolean("dark_mode", false)

        //Просто устанавливаем значения
        binding.tv1.text = "UserName: $username"
        binding.tv2.text = "Dark Mode: ${if(isDarkMode) "Enabled" else "Disabled"}"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityStorage1Binding.inflate(layoutInflater)

        enableEdgeToEdge()
        setContentView(binding.root)

        updateUI()

        binding.btn1.setOnClickListener {
            val intent = Intent(this@StorageActivity1, StorageActivity2::class.java)
            startForResult.launch(intent)
        }
        binding.btnBrowser.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://dzen.ru/?yredirect=true"))
            startActivity(intent)
        }
        binding.btnSendMessage.setOnClickListener {
            val intentMessage = Intent(Intent.ACTION_SEND).apply {
                type = "text/*"
                putExtra(
                    Intent.EXTRA_TEXT,
                    getSharedPreferences("user_preferences", Context.MODE_PRIVATE).getString("username", "Guest")
                )
            }
            startActivity(Intent.createChooser(intentMessage, "Отправить через: "))
        }
        binding.btnChoosePicture.setOnClickListener {
            val intentPic = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startForResult.launch(intentPic)
        }
    }
}

